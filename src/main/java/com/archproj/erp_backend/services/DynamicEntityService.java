package com.archproj.erp_backend.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@Service
public class DynamicEntityService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void processExcel(MultipartFile file, String entityName) {
        try {
            // Read file content once into memory
            byte[] fileContent = file.getBytes();

            // First read: parse header and types
            Map<String, String> columns = parseHeaderAndType(new ByteArrayInputStream(fileContent));

            // Second read: process rows with fresh stream
            persistRowsAsMaps(new ByteArrayInputStream(fileContent), entityName, columns);

        } catch (Exception e) {
            throw new RuntimeException("Failed to process Excel: " + e.getMessage(), e);
        }
    }

    private Map<String, String> parseHeaderAndType(InputStream is) throws Exception {
        try (Workbook wb = WorkbookFactory.create(is)) {
            Sheet sheet = wb.getSheetAt(0);
            Row header = sheet.getRow(0);
            Row sample = sheet.getRow(1);

            Map<String, String> columnTypes = new LinkedHashMap<>();
            for (Cell cell : header) {
                String name = cell.getStringCellValue().trim();
                Cell exampleCell = sample.getCell(cell.getColumnIndex());
                columnTypes.put(name, detectType(exampleCell));
            }
            return columnTypes;
        }
    }

    private String detectType(Cell cell) {
        if (cell == null) return "String";
        switch (cell.getCellType()) {
            case STRING:  return "String";
            case BOOLEAN: return "Boolean";
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) return "LocalDate";
                double value = cell.getNumericCellValue();
                return (value == Math.floor(value)) ? "Integer" : "Double";
            default:
                return "String";
        }
    }

    private void persistRowsAsMaps(InputStream is, String entityName, Map<String, String> columns) throws Exception {
        try (Workbook wb = WorkbookFactory.create(is)) {
            Session session = entityManager.unwrap(Session.class);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();
            if (rows.hasNext()) rows.next(); // Skip header

            while (rows.hasNext()) {
                Row row = rows.next();
                Map<String, Object> entityMap = new HashMap<>();
                int cellIdx = 0;
                for (String column : columns.keySet()) {
                    Cell cell = row.getCell(cellIdx++);
                    if (cell != null) {
                        switch (columns.get(column)) {
                            case "Integer":
                                entityMap.put(column, (int) cell.getNumericCellValue());
                                break;
                            case "Double":
                                entityMap.put(column, cell.getNumericCellValue());
                                break;
                            case "Boolean":
                                entityMap.put(column, cell.getBooleanCellValue());
                                break;
                            case "LocalDate":
                                entityMap.put(column, cell.getLocalDateTimeCellValue().toLocalDate());
                                break;
                            default:
                                entityMap.put(column, cell.toString());
                        }
                    }
                }
                session.save(entityName, entityMap);
            }
        }
    }
}