package com.poi.excel.poi_excel.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/14 1:43
 */
@Data
@ToString
public class OrderRecord {

    private Integer id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
