package com.poi.excel.poi_excel.controller;

import com.poi.excel.poi_excel.enums.StatusCode;
import com.poi.excel.poi_excel.response.BaseResponse;
import com.poi.excel.poi_excel.scheduler.MailOrderRecordScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/14 20:51
 */
@RestController
public class TestController {

    private static final Logger log= LoggerFactory.getLogger(TestController.class);

    private static final String prefix="test";

    @Autowired
    private MailOrderRecordScheduler mailOrderRecordScheduler;

    @RequestMapping(value = prefix+"/mail/order/record", method = RequestMethod.GET)
    public BaseResponse testMailOrderRecord(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        mailOrderRecordScheduler.sendOrderRecordAppendixInfo();
        return response;
    }



}
