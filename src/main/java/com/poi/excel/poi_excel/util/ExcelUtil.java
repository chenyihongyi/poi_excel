package com.poi.excel.poi_excel.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * @Author: Elvis
 * @Description:excel工具类，处理从excel读取到的单元格数据
 * @Date: 2019/9/11 13:49
 */
public class ExcelUtil {

    public static String manageCell(Cell cell, String dateFormat) throws Exception {
        DecimalFormat decimalFormatZero = new DecimalFormat("0.00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat decimalFormatNumeric = new DecimalFormat("0.00");

        String value = "";
        CellType cellType = cell.getCellTypeEnum();
        if (CellType.STRING.equals(cellType)) {
            value = cell.getStringCellValue();
        } else if (CellType.NUMERIC.equals(cellType)) {
            if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                value = decimalFormatZero.format(cell.getNumericCellValue());

            } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                value = sdf.format(cell.getDateCellValue());
            } else {
                value = decimalFormatNumeric.format(cell.getNumericCellValue());
            }
        } else if (CellType.BOOLEAN.equals(cellType)) {
            value = String.valueOf(cell.getBooleanCellValue());
        } else if (CellType.BLANK.equals(cellType)) {
            value = "";
        }
        return value;
    }
}
