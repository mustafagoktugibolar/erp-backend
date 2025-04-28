package com.archproj.erp_backend.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * Service for dynamically generating JPA entities at runtime based on uploaded Excel sheets,
 * then persisting each row into the corresponding table.
 */
@Service
@RequiredArgsConstructor
public class DynamicEntityService {

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Main entry: reads Excel, builds entity class, and persists all rows.
     */
    @Transactional
    public void processExcel(MultipartFile file, String entityName) {
        try (InputStream stream = file.getInputStream()) {
            // 1) Parse header + first row for column names and types
            Map<String, String> columns = parseHeaderAndType(stream);

            // 2) Generate or reload JPA entity class
            Class<?> entityClass = generateEntityClass(entityName, columns);

            // 3) Persist each data row
            persistRows(file.getInputStream(), entityClass, columns.keySet());

        } catch (Exception e) {
            throw new RuntimeException("Failed to process Excel: " + e.getMessage(), e);
        }
    }

    /**
     * Reads the first two rows of the sheet to map column names → simple Java types.
     */
    private Map<String, String> parseHeaderAndType(InputStream is) throws Exception {
        Workbook wb = WorkbookFactory.create(is);
        Sheet sheet = wb.getSheetAt(0);
        Row header = sheet.getRow(0);
        Row sample = sheet.getRow(1);
        if (header == null || sample == null) {
            throw new IllegalArgumentException("Excel must have header row and at least one data row.");
        }
        Map<String, String> map = new LinkedHashMap<>();
        for (Cell cell : header) {
            String name = cell.getStringCellValue().trim();
            Cell example = sample.getCell(cell.getColumnIndex());
            map.put(name, detectType(example));
        }
        return map;
    }

    /**
     * Simplistic type detection: maps Excel cell to String/Integer/Double/Boolean/LocalDate.
     */
    private String detectType(Cell cell) {
        if (cell == null) return "String";
        switch (cell.getCellType()) {
            case STRING:  return "String";
            case BOOLEAN: return "Boolean";
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) return "LocalDate";
                double v = cell.getNumericCellValue();
                return (v == Math.floor(v)) ? "Integer" : "Double";
            default:
                return "String";
        }
    }

    /**
     * Creates or reloads a JPA entity class under com.archproj.erp_backend.entities.dynamic
     * Uses inline ClassLoader to avoid module access issues.
     */
    private Class<?> generateEntityClass(String name, Map<String, String> cols) throws Exception {
        String pkg = "com.archproj.erp_backend.entities.dynamic.";
        String fqcn = pkg + name;

        ClassPool cp = ClassPool.getDefault();
        CtClass old = cp.getOrNull(fqcn);
        if (old != null) {
            if (old.isFrozen()) old.defrost();
            old.detach();
        }

        CtClass cc = cp.makeClass(fqcn);
        ConstPool cpool = cc.getClassFile().getConstPool();

        // Apply @Entity & @Table(name=...)
        AnnotationsAttribute classAttr = new AnnotationsAttribute(cpool, AnnotationsAttribute.visibleTag);
        Annotation ent = new Annotation("jakarta.persistence.Entity", cpool);
        Annotation tab = new Annotation("jakarta.persistence.Table", cpool);
        tab.addMemberValue("name", new StringMemberValue(name.toLowerCase(), cpool));
        classAttr.addAnnotation(ent);
        classAttr.addAnnotation(tab);
        cc.getClassFile().addAttribute(classAttr);

        // ID field
        CtField idField = new CtField(CtClass.longType, "id", cc);
        idField.setModifiers(Modifier.PRIVATE);
        cc.addField(idField);
        cc.addMethod(CtNewMethod.getter("getId", idField));
        cc.addMethod(CtNewMethod.setter("setId", idField));
        AnnotationsAttribute idAttr = new AnnotationsAttribute(cpool, AnnotationsAttribute.visibleTag);
        idAttr.addAnnotation(new Annotation("jakarta.persistence.Id", cpool));
        idAttr.addAnnotation(new Annotation("jakarta.persistence.GeneratedValue", cpool));
        idField.getFieldInfo().addAttribute(idAttr);

        // Other columns as String fields
        for (String col : cols.keySet()) {
            CtField f = new CtField(cp.get("java.lang.String"), col, cc);
            f.setModifiers(Modifier.PRIVATE);
            cc.addField(f);
            cc.addMethod(CtNewMethod.getter("get" + capitalize(col), f));
            cc.addMethod(CtNewMethod.setter("set" + capitalize(col), f));
        }

        // Generate bytecode
        byte[] bytecode = cc.toBytecode();

        // Load via inline ClassLoader
        var loader = new ClassLoader(this.getClass().getClassLoader()) {
            public Class<?> defineByteArray(String n, byte[] b, int off, int len) {
                return super.defineClass(n, b, off, len);
            }
        };
        Class<?> dynamic = loader.defineByteArray(fqcn, bytecode, 0, bytecode.length);

        // Detach so future calls can re-build
        cc.detach();
        return dynamic;
    }

    /**
     * Iterates over the sheet rows, instantiates for each row, and persists.
     */
    private void persistRows(InputStream is, Class<?> entityClass, Set<String> cols) throws Exception {
        Workbook wb = WorkbookFactory.create(is);
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();
        if (rows.hasNext()) rows.next(); // skip header
        while (rows.hasNext()) {
            Row row = rows.next();
            Object inst = entityClass.getDeclaredConstructor().newInstance();
            int idx = 0;
            for (String col : cols) {
                Cell c = row.getCell(idx++);
                if (c != null) {
                    String val = c.toString();
                    entityClass.getMethod("set" + capitalize(col), String.class)
                            .invoke(inst, val);
                }
            }
            entityManager.persist(inst);
        }
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
