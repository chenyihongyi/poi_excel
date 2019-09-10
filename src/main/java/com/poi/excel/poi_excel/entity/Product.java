package com.poi.excel.poi_excel.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/10 16:46
 */
@Data
@ToString
public class Product {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单价
     */
    private Double price;

    /**
     * 库存量
     */
    private Integer stock;

    private String remark;

    /**
     * 采购日期
     */
    private Date purchaseDate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(1=是；0=否)
     */
    private Integer isDelete;
}
