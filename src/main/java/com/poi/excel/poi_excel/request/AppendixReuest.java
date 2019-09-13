package com.poi.excel.poi_excel.request;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/13 21:16
 */
@Data
@ToString
public class AppendixReuest {

    @NotNull
    private Integer recordId;

    @NotBlank
    private String appendixIds;

}
