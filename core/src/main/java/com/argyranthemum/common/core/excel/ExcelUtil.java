package com.argyranthemum.common.core.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ExcelUtil {

    /**
     * 解析Excel
     * <p>
     * 默认不跳过表头
     *
     * @param titles 列标题
     * @param is     文件流
     * @return
     */
    public static List<Map<String, String>> parse(List<String> titles, FileInputStream is) {
        return parse(titles, is, false);
    }

    /**
     * 解析Excel
     *
     * @param titles     列标题
     * @param is         文件流
     * @param skipHeader 是否跳过表头
     * @return
     */
    public static List<Map<String, String>> parse(List<String> titles, FileInputStream is, boolean skipHeader) {
        return readExcel(titles, is, skipHeader);
    }

    private static List<Map<String, String>> readExcel(List<String> titles, FileInputStream is, boolean skipHeader) {
        List<Map<String, String>> result = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(is);
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                int rows = sheet.getPhysicalNumberOfRows();
                for (int j = skipHeader ? 1 : 0; j < rows; j++) {
                    Row row = sheet.getRow(j);
                    int cells = row.getPhysicalNumberOfCells();
                    int titleIndex = 0;
                    Map<String, String> cellMap = new HashMap<>();

                    for (int k = 0; k < cells; k++) {
                        if (titleIndex >= titles.size()) {
                            titleIndex = titles.size() - 1;
                        }
                        Cell cell = row.getCell(k);
                        String name = titles.get(titleIndex);

                        if (isENum(cell.toString()) || isDouble(cell.toString())) {
                            BigDecimal bd = new BigDecimal(cell.toString());
                            cellMap.put(name, bd.longValue() + "");
                        } else {
                            cellMap.put(name, cell.toString());
                        }
                        titleIndex += 1;
                    }
                    result.add(cellMap);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static boolean isENum(String input) {
        //判断输入字符串是否为科学计数法
        if (StringUtils.isBlank(input)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$");
        return pattern.matcher(input).matches();
    }

    private static boolean isDouble(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
