package com.poi.excel.poi_excel.controller;

import com.poi.excel.poi_excel.entity.OrderRecord;
import com.poi.excel.poi_excel.enums.StatusCode;
import com.poi.excel.poi_excel.mapper.OrderRecordMapper;
import com.poi.excel.poi_excel.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/14 16:18
 */
@RestController
public class OrderRecordController {

    private static final Logger log= LoggerFactory.getLogger(OrderRecordController.class);

    private static final String prefix="order/record";

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @RequestMapping(value = prefix+"/detail/{id}",method = RequestMethod.GET)
    public BaseResponse detail(@PathVariable Integer id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            OrderRecord record=orderRecordMapper.selectByPrimaryKey(id);
            response.setData(record);
        }catch (Exception e){
            log.error("查询发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
