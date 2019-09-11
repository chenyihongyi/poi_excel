package com.poi.excel.poi_excel.request;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/11 21:27
 */
@Data
@ToString
public class MailRequest implements Serializable {

    @NotBlank
    private String mailTos;

    private String subject;

    private String content;


}
