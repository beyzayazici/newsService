package com.example.newsservice;

import com.example.newsservice.model.News;
import com.example.newsservice.service.Helper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExcelExporter {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final HashMap<String,List<News>> newsHashMap;

    public ExcelExporter(HashMap<String,List<News>> newsHashMap) {
        this.newsHashMap=newsHashMap;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Id", style);
        createCell(row, 1, "URL", style);
        createCell(row, 2, "Name", style);
        createCell(row, 3, "Lang", style);
        createCell(row, 4, "Type", style);
        createCell(row, 5, "Tags", style);
        createCell(row, 6, "Categories", style);
        createCell(row, 7, "Title", style);
        createCell(row, 8, "Description", style);
        createCell(row, 9, "Content", style);
        createCell(row, 10, "CrawlDate", style);
        createCell(row, 11, "ModifiedDate", style);
        createCell(row, 12, "PublishedDate", style);
        createCell(row,13,"Text", style);
        createCell(row,14,"Rules", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(List<News> sheetNews) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);

        for(News news:sheetNews){

        Row row = sheet.createRow(rowCount++);
        int columnCount = 0;

        createCell(row, columnCount++, news.getId(), style);
        createCell(row, columnCount++, news.getUrl(), style);
        createCell(row, columnCount++, news.getName(), style);
        createCell(row, columnCount++, news.getLang(), style);
        createCell(row, columnCount++, news.getType(), style);
        createCell(row, columnCount++, Helper.convertListToString(news.getTags()), style);
        createCell(row, columnCount++, Helper.convertListToString(news.getCategories()), style);
        createCell(row, columnCount++, news.getTitle(), style);
        createCell(row, columnCount++, news.getDescription(), style);
        createCell(row, columnCount++, news.getContent(), style);
        createCell(row, columnCount++, news.getCrawl_date(), style);
        createCell(row, columnCount++, news.getModified_date(), style);
        createCell(row, columnCount++, news.getPublished_date(), style);
        createCell(row, columnCount++, news.getText(), style);
        createCell(row, columnCount, news.getRules(), style);
        }
    }

    public String getFileName() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
        return "news_" + currentDateTime + ".xls";
    }

    public void createSheetAndContent(HttpServletResponse response) throws IOException {

        List<News> sheetNews;
        for (String s : newsHashMap.keySet()) {
            sheetNews = newsHashMap.get(s);
            sheet = workbook.createSheet(s);
            writeHeaderLine();
            writeDataLines(sheetNews);
        }

        String fileName = getFileName();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel; charset=UTF-8");
        response.setHeader(headerKey, headerValue);

        FileOutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
