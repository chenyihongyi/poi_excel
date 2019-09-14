package com.poi.excel.poi_excel.service;

import com.poi.excel.poi_excel.entity.Appendix;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import javax.security.auth.login.AppConfigurationEntry;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/11 21:18
 */
@Service
public class MailService {

    private static final Logger log= LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendSimpleMail(final String subject,final String content,final String[] tos) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("mail.send.from"));
        message.setTo(tos);
        message.setSubject(subject);
        message.setText(content);
       mailSender.send(message);

       log.info("发送简单文本邮件成功--->");
    }

    public void sendAttachmentMail(final String subject, final String content, final String[] tos, final List<Appendix> appendixList) throws Exception{
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,true,"utf-8");
        messageHelper.setFrom(env.getProperty("mail.send.from"));
        messageHelper.setTo(tos);
        messageHelper.setSubject(subject);
        messageHelper.setText(content);

        //加入附件
        messageHelper.addAttachment(env.getProperty("mail.send.attachment.one.name"), new File(env.getProperty("mail.send.attachment.one.location")));
        messageHelper.addAttachment(env.getProperty("mail.send.attachment.two.name"), new File(env.getProperty("mail.send.attachment.two.location")));
        messageHelper.addAttachment(env.getProperty("mail.send.attachment.three.name"), new File(env.getProperty("mail.send.attachment.three.location")));
        mailSender.send(mimeMessage);
        log.info("发送带附件文本邮件成功--->");
    }

    /**
     * 发送带HTML邮件
     * @param subject
     * @param content
     * @param tos
     * @throws Exception
     */
    public void sendHTMLMail(final String subject, final String content, final String[] tos) throws Exception{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,true,"utf-8");
        messageHelper.setFrom(env.getProperty("mail.send.from"));
        messageHelper.setTo(tos);
        messageHelper.setSubject(subject);
        messageHelper.setText(content,true);

        //加入附件
        messageHelper.addAttachment(env.getProperty("mail.send.attachment.one.name"), new File(env.getProperty("mail.send.attachment.one.location")));
        mailSender.send(mimeMessage);
        log.info("发送带HTML邮件成功--->");
    }

    /**
     * 渲染模板
     * @param templateFile
     * @param paramMap
     * @return
     * @throws Exception
     */
    public String renderTemplate(final String templateFile, Map<String, Object> paramMap) throws Exception {

       //1.thymeleaf 实现渲染模板
        /*
        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariables(paramMap);
        return templateEngine.process(templateFile, context);*/

        //2.freemarker 实现渲染模板
       Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
       //设置去哪里读freemarker文件-目录
        cfg.setClassForTemplateLoading(this.getClass(), "/ftl");
        //根据提供的模板文件构建模板渲染实例
        Template template = cfg.getTemplate(templateFile);
        String html= FreeMarkerTemplateUtils.processTemplateIntoString(template,paramMap);
        return html;
    }

    @PostConstruct
    public void init(){
        System.setProperty("mail.mime.splitlongparameters", "false");
    }


}