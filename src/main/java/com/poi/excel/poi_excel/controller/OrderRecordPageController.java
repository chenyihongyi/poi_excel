package com.poi.excel.poi_excel.controller;

import com.poi.excel.poi_excel.entity.Appendix;
import com.poi.excel.poi_excel.entity.OrderRecord;
import com.poi.excel.poi_excel.mapper.AppendixMapper;
import com.poi.excel.poi_excel.mapper.OrderRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/14 16:12
 */
@Controller
public class OrderRecordPageController {

    private static final Logger log = LoggerFactory.getLogger(OrderRecordController.class);

    private static final String prefix = "order/record/page";

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Autowired
    private AppendixMapper appendixMapper;

    /**
     *将订单信息以及拥有的附件列表返回给前端
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = prefix + "/detail/appendix/list/{id}", method = RequestMethod.GET)
    public String recordDetailAppedix(@PathVariable Integer id, ModelMap modelMap) {

        OrderRecord record = orderRecordMapper.selectByPrimaryKey(id);
        modelMap.addAttribute("record", record);

        List<Appendix> appendixList = appendixMapper.selectModuleAppendix("orderRecord", id);
        modelMap.addAttribute("appendixList", appendixList);

        return "orderRecord";
    }
}
