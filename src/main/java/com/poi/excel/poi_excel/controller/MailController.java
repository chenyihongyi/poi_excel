package com.poi.excel.poi_excel.controller;

import com.google.common.collect.Maps;
import com.poi.excel.poi_excel.entity.Appendix;
import com.poi.excel.poi_excel.enums.StatusCode;
import com.poi.excel.poi_excel.request.MailRequest;
import com.poi.excel.poi_excel.response.BaseResponse;
import com.poi.excel.poi_excel.service.MailService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @Value("${mail.template.file.location}")
    private String templateLocation;

    /**
     * 发送简单文本邮件
     * @param mailRequest
     * @param result
     * @return
     */
    @RequestMapping(value = prefix+"/send/simple",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendSimpleMail(@RequestBody @Validated MailRequest mailRequest, BindingResult result){

        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            if (result.hasErrors()) {
                return new BaseResponse(StatusCode.Invalid_Params);
            }
            log.info("发送简单文本邮件：{} ", mailRequest);

            mailService.sendSimpleMail(mailRequest.getSubject(), mailRequest.getContent(), StringUtils.split(mailRequest.getMailTos(), ","));
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
            log.error("发送简单文本邮件失败:{}", e.getMessage());
        }
        return response;
    }

    /**
     * 发送带附件邮件
     * @param mailRequest
     * @param result
     * @return
     */
    @RequestMapping(value = prefix+"/send/attachment",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendAttachmentMail(@RequestBody @Validated MailRequest mailRequest,BindingResult result){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            if (result.hasErrors()){
                return new BaseResponse(StatusCode.Invalid_Params);
            }
            log.info("发送带附件邮件：{} ",mailRequest);

            //mailService.sendAttachmentMail(mailRequest.getSubject(),mailRequest.getContent(), StringUtils.split(mailRequest.getMailTos(),","));
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
            log.error("发送带附件邮件失败:{}", e.getMessage());
        }
        return response;
    }

    /**
     * 发送Html文本邮件
     * @param mailRequest
     * @param result
     * @return
     */
    @RequestMapping(value = prefix+"/send/html",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendHTMLMail(@RequestBody @Validated MailRequest mailRequest,BindingResult result){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            if (result.hasErrors()){
                return new BaseResponse(StatusCode.Invalid_Params);
            }
            log.info("发送HTML文本邮件：{} ",mailRequest);

            mailService.sendHTMLMail(mailRequest.getSubject(),mailRequest.getContent(), StringUtils.split(mailRequest.getMailTos(),","));
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
            log.error("发送HTML文本邮件失败:{}", e.getMessage());
        }
        return response;
    }

    /**
     * 渲染HTML模板并发送带模板的邮件
     * @param mailRequest
     * @param result
     * @return
     */
    @RequestMapping(value = prefix+"/send/template", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendTemplateMail(@RequestBody @Validated MailRequest mailRequest, BindingResult result){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            if(result.hasErrors()){
                return new BaseResponse(StatusCode.Invalid_Params);
            }
            log.info("渲染HTML模板并发送带模板的邮件：{}", mailRequest);

            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("content", mailRequest.getContent());
            paramMap.put("mailTos", mailRequest.getMailTos());
            String html = mailService.renderTemplate(env.getProperty("mail.template.file.location"), paramMap);
            mailService.sendHTMLMail(mailRequest.getSubject(), html, StringUtils.split(mailRequest.getMailTos(), ","));
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
            log.error("渲染HTML模板并发送带模板的邮件失败：{}", e.getMessage());

        }
        return response;
    }

    //TODO 文件上传  02：16/33：09





}
