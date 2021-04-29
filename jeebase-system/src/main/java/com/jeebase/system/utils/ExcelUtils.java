package com.jeebase.system.utils;

import com.jeebase.common.base.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 工具类(兼容xls和xlsx)
 *
 * @author Logan
 * @version 1.0.0
 * @createDate 2019-03-07
 */
public class ExcelUtils {

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";
    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 输出数据到自定义模版的Excel输出流
     *
     * @param fileName 文件名
     * @param data     数据
     * @throws IOException 错误时抛出异常，由调用者处理
     */
    public static void writeDataToTemplateOutputStream(String fileName, List<String> title, List<List<String>> data, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            Workbook book = new XSSFWorkbook();
            ExcelUtils.writeDataToWorkbook(title, data, book, fileName);

            // 设置contentType为excel格式
            response.setContentType("application/vnd.ms-excel;charset=utf-8");

            out = response.getOutputStream();
            book.write(out);

        } catch (IOException e) {
            throw new BusinessException("导出IO异常");
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                throw new BusinessException("关闭IO异常");
            }
        }
    }

    /**
     * 从Excel文件获取Workbook对象
     *
     * @param excelFile Excel文件
     * @return Workbook对象
     * @throws IOException 错误时抛出异常，由调用者处理
     */
    public static Workbook getWorkbookFromExcel(File excelFile) throws IOException {
        try (
                InputStream inputStream = new FileInputStream(excelFile);
        ) {

            if (excelFile.getName().endsWith(XLS)) {
                return new HSSFWorkbook();
            } else if (excelFile.getName().endsWith(XLSX)) {
                return new XSSFWorkbook();
            } else {
                throw new IOException("文件类型错误");
            }
        }
    }

    /**
     * 把Workbook对象内容输出到Excel文件
     *
     * @param book Workbook对象
     * @param file Excel文件
     * @throws FileNotFoundException 找不到文件异常，文件已创建，实际不存在该异常
     * @throws IOException           输入输出异常
     */
    public static void writeWorkbookToFile(Workbook book, File file) throws FileNotFoundException, IOException {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        try (
                OutputStream outputStream = new FileOutputStream(file);
        ) {
            writeWorkbookToOutputStream(book, outputStream);
        }
    }

    /**
     * 把Workbook对象输出到Excel输出流
     *
     * @param book         Workbook对象
     * @param outputStream Excel输出流
     * @throws IOException 错误时抛出异常，由调用者处理
     */
    public static void writeWorkbookToOutputStream(Workbook book, OutputStream outputStream) throws IOException {
        book.write(outputStream);
    }

    /**
     * 输出数据到Workbook对象中指定页码
     *
     * @param title    标题，写在第一行，可传null
     * @param data     数据
     * @param book     Workbook对象
     * @param fileName 输出数据到Workbook指定页码的页
     */
    public static void writeDataToWorkbook(List<String> title, List<List<String>> data, Workbook book, String fileName) {
        Sheet sheet = book.createSheet(fileName);
        for (int i = 0; i < 15; i++) {
            sheet.setColumnWidth(i, 50 * 90);
        }
        Row row = null;
        Cell cell = null;

        // 设置表头
        if (null != title && !title.isEmpty()) {
            row = sheet.getRow(0);
            if (null == row) {
                row = sheet.createRow(0);
            }

            for (int i = 0; i < title.size(); i++) {
                cell = row.getCell(i);
                if (null == cell) {
                    cell = row.createCell(i);
                }
                cell.setCellValue(title.get(i));
            }
        }

        List<String> rowData = null;
        if (null != data && !data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {

                row = sheet.getRow(i + 1);
                if (null == row) {
                    row = sheet.createRow(i + 1);
                }

                rowData = data.get(i);
                if (null == rowData) {
                    continue;
                }
                for (int j = 0; j < rowData.size(); j++) {
                    cell = row.getCell(j);
                    if (null == cell) {
                        cell = row.createCell(j);
                    }
                    setValue(cell, rowData.get(j));
                }
            }
        }
    }

    /**
     * 读取Excel文件第一页
     *
     * @param pathname 文件路径名
     * @return 第一页数据集合
     * @throws IOException 错误时抛出异常，由调用者处理
     */
    public static List<List<String>> readExcelFirstSheet(String pathname) throws IOException {
        File file = new File(pathname);
        return readExcelFirstSheet(file);

    }

    /**
     * 读取Excel文件第一页
     *
     * @param file Excel文件
     * @return 第一页数据集合
     * @throws IOException 错误时抛出异常，由调用者处理
     */
    public static List<List<String>> readExcelFirstSheet(File file) throws IOException {
        try (
                InputStream inputStream = new FileInputStream(file);
        ) {

            if (file.getName().endsWith(XLS)) {
                return readXlsFirstSheet(inputStream);
            } else if (file.getName().endsWith(XLSX)) {
                return readXlsxFirstSheet(inputStream);
            } else {
                throw new IOException("文件类型错误");
            }
        }

    }

    /**
     * 读取xls格式Excel文件第一页
     *
     * @param inputStream Excel文件输入流
     * @return 第一页数据集合
     * @throws IOException 错误时抛出异常，由调用者处理
     */
    public static List<List<String>> readXlsFirstSheet(InputStream inputStream) throws IOException {
        Workbook workbook = new HSSFWorkbook(inputStream);
        return readExcelFirstSheet(workbook);
    }

    /**
     * 读取xlsx格式Excel文件第一页
     *
     * @param inputStream Excel文件输入流
     * @return 第一页数据集合
     * @throws IOException 错误时抛出异常，由调用者处理
     */
    public static List<List<String>> readXlsxFirstSheet(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        return readExcelFirstSheet(workbook);
    }

    /**
     * 读取Workbook第一页
     *
     * @param book Workbook对象
     * @return 第一页数据集合
     */
    public static List<List<String>> readExcelFirstSheet(Workbook book) {
        return readExcel(book, 0);
    }

    /**
     * 读取指定页面的Excel
     *
     * @param book Workbook对象
     * @param page 页码
     * @return 指定页面数据集合
     */
    public static List<List<String>> readExcel(Workbook book, int page) {
        List<List<String>> list = new ArrayList<>();
        Sheet sheet = book.getSheetAt(page);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            // 如果当前行为空，则加入空，保持行号一致
            if (null == row) {
                list.add(null);
                continue;
            }

            List<String> columns = new ArrayList<>();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                columns.add(getValue(cell));
            }

            list.add(columns);
        }

        return list;
    }

    /**
     * 解析单元格中的值
     *
     * @param cell 单元格
     * @return 单元格内的值
     */
    private static String getValue(Cell cell) {
        if (null == cell) {
            return null;
        }
        String value = null;
        //未定义

        return value;
    }

    /**
     * 设置单元格值
     *
     * @param cell  单元格
     * @param value 值
     */
    private static void setValue(Cell cell, String value) {
        if (null == cell) {
            return;
        }

        cell.setCellValue(value);


    }

}