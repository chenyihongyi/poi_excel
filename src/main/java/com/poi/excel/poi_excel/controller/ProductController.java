package com.poi.excel.poi_excel.controller;

import com.poi.excel.poi_excel.entity.Product;
import com.poi.excel.poi_excel.enums.StatusCode;
import com.poi.excel.poi_excel.mapper.ProductMapper;
import com.poi.excel.poi_excel.request.ProductDto;
import com.poi.excel.poi_excel.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/11 20:51
 */
@RestController
public class ProductController {

    private static final Logger log= LoggerFactory.getLogger(ProductController.class);

    private static final String prefix="product";

    @Autowired
    private ProductMapper productMapper;

    @RequestMapping(value = prefix+"/list",method = RequestMethod.GET)
    public BaseResponse detail(ProductDto dto){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            List<Product> dataList=productMapper.queryDetail(dto);
            log.info("查询信息：{} ",dataList);

            response.setData(dataList);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
           log.error("查询信息失败：{}", e.getMessage());
        }
        return response;
    }

}
