package com.poi.excel.poi_excel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

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

    public void sendSimpleMail(final String subject,final String content,final String[] tos) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("mail.send.from"));
        message.setTo(tos);
        message.setSubject(subject);
        message.setText(content);
       mailSender.send(message);

       log.info("发送简单文本邮件成功--->");
    }

    public void sendAttachmentMail(final String subject, final String content, final String[] tos) throws Exception{
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
         messageHelper.setFrom(env.getProperty("mail.send.from"));
         messageHelper.setTo(tos);
         messageHelper.setSubject(subject);
         messageHelper.setText(content);

         //加入附件












    }
}
