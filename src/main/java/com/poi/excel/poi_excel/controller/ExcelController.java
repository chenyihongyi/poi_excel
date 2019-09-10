package com.poi.excel.poi_excel.controller;

import com.poi.excel.poi_excel.entity.Product;
import com.poi.excel.poi_excel.enums.StatusCode;
import com.poi.excel.poi_excel.mapper.ProductMapper;
import com.poi.excel.poi_excel.response.BaseResponse;
import com.poi.excel.poi_excel.service.ProductService;
import com.poi.excel.poi_excel.util.PoiUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/10 17:01
 */
@Controller
public class ExcelController {

    private static final Logger log = LoggerFactory.getLogger(ExcelController.class);

    private static final String prefix = "excel";

    @Autowired
    private Environment env;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

     @RequestMapping(value = prefix+"/product/list", method = RequestMethod.GET)
     @ResponseBody
    public BaseResponse list(String name){
         BaseResponse response = new BaseResponse(StatusCode.Success);
         try{
             List<Product> products = productMapper.selectAll(name);
             response.setData(products);
         }catch (Exception e){
             log.info("查询信息:{}", e.getMessage());
         }
         return response;
     }

    /**
     * 导出excel
     * @return
     */
    @RequestMapping(value = prefix + "/product/export", method = RequestMethod.GET)
    public @ResponseBody
    String export(String name, HttpServletResponse response) {
        final String[] headers = new String[]{"名称", "单位", "单价", "库存量", "备注", "采购日期"};
        List<Product> products = productMapper.selectAll(name);
        try {
            if (products != null && products.size() >= 0) {
                List<Map<Integer, Object>> listMap = productService.manageProductList(products);
                //将listMap填入真正的excel对应的workbook
                Workbook workbook = PoiUtil.fillExcelSheetData(listMap, headers, env.getProperty("poi.product.excel.sheet.name"));
                //将excel实例以流的形式写回浏览器
                PoiUtil.downloadExcel(response, workbook, env.getProperty("poi.product.excel.file.name"));
                return env.getProperty("poi.product.excel.file.name");
            }
        } catch (Exception e) {
            log.debug("excel sheet写入数据发生异常:", e.getMessage());
        }
        return null;
    }

}
