package org.example.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ExcelReader {
    Workbook work = null;
    private InputStream fin = null;

    public ExcelReader(Path filePath) {

        String extension = FilenameUtils.getExtension(filePath.toString());
        if (!extension.contains("xls")) {
            throw new RuntimeException("Invalid Excel File Provided For Reading. " + filePath);
        }

        try {
            fin = Files.newInputStream(filePath, StandardOpenOption.READ);
            work = new XSSFWorkbook(fin);
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readCell(String sheetName, int row, int col) {

        DataFormatter formatter = new DataFormatter();

        Sheet sheet = work.getSheet(sheetName);

        Row roww = sheet.getRow(row);
        Cell cell = roww.getCell(col);

        return formatter.formatCellValue(cell);

    }

    public String readCell(int sheetIndex, int row, int col) {

        DataFormatter formatter = new DataFormatter();

        Sheet sheet = work.getSheetAt(sheetIndex);
        Row roww = sheet.getRow(row);
        Cell cell = roww.getCell(col);

        return formatter.formatCellValue(cell);

    }

}
