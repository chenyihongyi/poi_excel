package com.poi.excel.poi_excel.scheduler;

import com.poi.excel.poi_excel.entity.Appendix;
import com.poi.excel.poi_excel.entity.OrderRecord;
import com.poi.excel.poi_excel.mapper.AppendixMapper;
import com.poi.excel.poi_excel.mapper.OrderRecordMapper;
import com.poi.excel.poi_excel.service.MailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/14 17:10
 */
@Component
public class MailOrderRecordScheduler {

private static final Logger log = LoggerFactory.getLogger(MailOrderRecordScheduler.class);

private static final Integer recordId = 11;

@Autowired
private OrderRecordMapper orderRecordMapper;

@Autowired
private AppendixMapper appendixMapper;

@Autowired
private MailService mailService;

private Environment env;

    @Scheduled(cron = "${scheduler.mail.send.cron}")
    public void sendOrderRecordAppendixInfo(){
        OrderRecord record=orderRecordMapper.selectByPrimaryKey(recordId);
        if (record!=null){
            final String subject="定时任务之@Scheduled-发送带有模块附件的邮件";
            final String content=String.format("订单记录信息：订单编号=%s 订单类型=%s ",record.getOrderNo(),record.getOrderType());

            List<Appendix> appendixList=appendixMapper.selectModuleAppendix("orderRecord",recordId);
            if (appendixList!=null && appendixList.size()>0){
                try {
                    mailService.sendAttachmentMail(subject,content, StringUtils.split(env.getProperty("scheduler.mail.send.to"),","),appendixList);
                }catch (Exception e){
                    log.error("发送定时任务失败:{}",e.getMessage());
                }
            }
        }
    }
}
