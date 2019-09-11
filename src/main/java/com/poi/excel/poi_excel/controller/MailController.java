package com.poi.excel.poi_excel.controller;

import com.poi.excel.poi_excel.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/11 20:46
 */
@RestController
public class MailController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private static final String prefix="mail";

    @Autowired
    private MailService mailService;

    @Autowired
    private Environment env;




}
