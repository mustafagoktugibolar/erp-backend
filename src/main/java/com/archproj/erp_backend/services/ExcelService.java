package com.archproj.erp_backend.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
public class ExcelService {

    public List<Map<String, Object>> parseExcelFile(MultipartFile file) throws IOException {
        List<Map<String, Object>> result = new ArrayList<>();

        try (InputStream is = file.getInputStream();
                Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (!rows.hasNext()) {
                return result;
            }

            // Read Headers
            Row headerRow = rows.next();
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }

            // Read Data
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Map<String, Object> rowData = new HashMap<>();

                // Track empty rows to skip them
                boolean isEmptyRow = true;

                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = currentRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (cell != null) {
                        Object value = getCellValue(cell);
                        if (value != null && !value.toString().trim().isEmpty()) {
                            rowData.put(headers.get(i), value);
                            isEmptyRow = false;
                        }
                    }
                }

                if (!isEmptyRow) {
                    result.add(rowData);
                }
            }
        } catch (Exception e) {
            log.error("Failed to parse Excel file", e);
            throw new IOException("Failed to parse Excel file: " + e.getMessage());
        }

        return result;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                // Avoid scientific notation for plain numbers (e.g. IDs)
                double val = cell.getNumericCellValue();
                if (val == (long) val) {
                    return String.format("%d", (long) val);
                }
                return String.valueOf(val);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private Object getCellValue(Cell cell) {
        if (cell == null)
            return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue(); // Return Date object
                }
                double val = cell.getNumericCellValue();
                if (val == (long) val) {
                    return (long) val; // Return Long for integers
                }
                return val; // Return Double for floats
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return null;
        }
    }
}
