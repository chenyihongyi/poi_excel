package com.poi.excel.poi_excel.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/13 16:41
 */
@Data
@ToString
public class AppendixDto implements Serializable {

    private String moduleType;

    private String location;

    private Integer recordId;



}
