package com.poi.excel.poi_excel.request;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/11 20:57
 */
@Data
@ToString
public class ProductDto {

    private Integer id;

    private String name;

    private String productNo;

    private Date createTime;

    private Date updateTime;
}
