package com.example.newsservice;

import com.example.newsservice.model.Movie;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private Movie movie;

    public ExcelExporter(Movie movie) {
        this.movie = movie;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("movies");
    }

    private void writeHeaderLine() {
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Id", style);
        createCell(row, 1, "Overview", style);
        createCell(row, 2, "Adult", style);
        createCell(row, 3, "Release_Date", style);
        createCell(row, 4, "Vote_Average", style);
        createCell(row, 5, "Original_Title", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);

        Row row = sheet.createRow(rowCount++);
        int columnCount = 0;

        createCell(row, columnCount++, movie.getId(), style);
        createCell(row, columnCount++, movie.getOverview(), style);
        createCell(row, columnCount++, movie.isAdult(), style);
        createCell(row, columnCount++, movie.getRelease_date(), style);
        createCell(row, columnCount++, movie.getVote_average(), style);
        createCell(row, columnCount++, movie.getOriginal_title(), style);

    }

    public String getFileName()
    {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
        String fileName = "movies_" + currentDateTime + ".xls";

        return fileName;
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        String fileName = getFileName();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel; charset=UTF-8");
        response.setHeader(headerKey, headerValue);

        FileOutputStream outputStream = null;

        outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
