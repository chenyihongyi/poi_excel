package com.poi.excel.poi_excel.controller;


import com.poi.excel.poi_excel.entity.Product;
import com.poi.excel.poi_excel.enums.StatusCode;
import com.poi.excel.poi_excel.mapper.ProductMapper;
import com.poi.excel.poi_excel.response.BaseResponse;
import com.poi.excel.poi_excel.service.ProductService;
import com.poi.excel.poi_excel.util.PoiUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
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

    /**
     * 导入excel
     * @param request
     * @return
     */
    @RequestMapping(value = prefix+"/product/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public BaseResponse uploadExcel(MultipartHttpServletRequest request){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
        MultipartFile file = request.getFile("productFile");
        if(file!=null){
            String fileName = file.getOriginalFilename();
            String suffix = StringUtils.substring(fileName, fileName.lastIndexOf(".") + 1);
            //根据上传的excel文件构造workbook实例，注意区分xls与xlsx版本对应的实例
            Workbook workbook = PoiUtil.getWorkbook(file, suffix);
            //读取上传上来的excel的数据到list<Product>中
            List<Product> products = PoiUtil.readExcelData(workbook);
            log.debug("读取excel得到 的数据,{}", products);
            //插入数据到数据库
      /*      for (Product p : products) {
                productMapper.insertSelective(p);
            }*/
            productMapper.insertBatch(products);
        } else {
            return new BaseResponse(StatusCode.Invalid_Params);
        }
        } catch (Exception e) {
            log.error("上传excel导入数据 发生异常:", e.fillInStackTrace());
            return new BaseResponse(StatusCode.Fail);
        }
        return response;
    }

    /**
     * 导出excel模板
     * @param response
     * @return
     */
    @RequestMapping(value = prefix+"/product/export/template", method = RequestMethod.GET)
    public @ResponseBody
    String exportTemplate(HttpServletResponse response) {
        final String[] headers = new String[]{
                "名称", "单位", "单价", "库存量", "备注", "采购日期"
        };
        try {
            Workbook workbook = PoiUtil.fillExcelSheetData(null, headers, env.getProperty("poi.product.excel.file.name"));

            PoiUtil.downloadExcel(response, workbook, env.getProperty("poi.product.excel.file.name"));
            return env.getProperty("poi.product.excel.file.name");
        } catch (Exception e) {
            log.error("导出excel模板异常,{}",e.getMessage());
        }
        return null;
    }
}
