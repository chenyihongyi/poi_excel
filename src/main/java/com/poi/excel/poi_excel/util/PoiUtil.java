package com.poi.excel.poi_excel.util;

import com.poi.excel.poi_excel.enums.WorkBookVersion;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Elvis
 * @Description: 解析excel工具类
 * @Date: 2019/9/10 21:46
 */

public class PoiUtil {

    private static final Logger log = LoggerFactory.getLogger(PoiUtil.class);

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 下载excel
     *
     * @param response
     * @param workbook
     * @param fileName
     * @throws Exception
     */
    public static void downloadExcel(HttpServletResponse response, Workbook workbook, String fileName) throws Exception {
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }



    /**
     * 填充数据到excel的sheet中
     *
     * @param dataList
     * @param headers
     * @param sheetName
     * @return
     */
    public static Workbook fillExcelSheetData(List<Map<Integer, Object>> dataList, String[] headers, String sheetName) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        //创建sheet的第一行数据,即excel的表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        //从第二行开始写入真正的数据列
        int rowIndex = 1;
        Row row;
        Object obj;

        for (Map<Integer, Object> rowMap : dataList) {
            try {
                row = sheet.createRow(rowIndex++);

                //遍历表头行每一个key->取到实际的value
                for (int i = 0; i < headers.length; i++) {
                    obj = rowMap.get(i);

                    if (obj == null) {
                        row.createCell(i).setCellValue("");
                    } else if (obj instanceof Date) {
                        String tempDate = simpleDateFormat.format((Date) obj);
                        row.createCell(i).setCellValue(tempDate == null ? "" : tempDate);
                    } else {
                        row.createCell(i).setCellValue(String.valueOf(obj));
                    }
                }
            } catch (Exception e) {
                log.debug("excel sheet写入数据发生异常:", e.fillInStackTrace());
            }
        }
        return workbook;
    }

    /**
     * 分sheet导出
     *
     * @param dataList
     * @param headers
     * @param sheetName
     * @return
     */
    public Workbook manageSheet(List<Map<Integer, Object>> dataList, String[] headers, String sheetName) {
        final Integer sheetSize = 5;
        int dataTotal = dataList.size();

        int sheetTotal = (dataTotal % sheetSize == 0) ? dataTotal / sheetSize : (dataTotal / sheetSize + 1);
        int start = 0;
        int end = sheetSize;

        List<Map<Integer, Object>> subList;
        Workbook workbook = new HSSFWorkbook();
        for (int i = 0; i < sheetTotal; i++) {
            subList = dataList.subList(start, end);
            workbook = this.fillExcelSheetData(subList, headers, sheetName + "_" + (i + 1) + workbook);
            start += sheetSize;
            end += sheetSize;
            if (end >= dataTotal) {
                end = dataTotal;
            }
        }
        return workbook;
    }

    /**
     * 根据file与后缀名区分获取workbook实例
     *
     * @param file
     * @param suffix
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(MultipartFile file, String suffix) throws Exception {
        Workbook workbook = null;
        if (WorkBookVersion.WorkBook2003Xls.getCode().equalsIgnoreCase(suffix)) {
            workbook = new HSSFWorkbook(file.getInputStream());
        } else if (WorkBookVersion.WorkBook2007Xlsx.getCode().equalsIgnoreCase(suffix)) {
            workbook = new XSSFWorkbook(file.getInputStream());
        }
        return workbook;
    }

}