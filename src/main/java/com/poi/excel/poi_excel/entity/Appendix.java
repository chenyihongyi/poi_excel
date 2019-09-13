package com.poi.excel.poi_excel.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/13 15:58
 */
@Data
@ToString
public class Appendix {

    private Integer id;

    /**
     * 附件名
     */
    private String name;

    /**
     * 大小
     */
    private Long size;

    /**
     * 所属模块
     */
    private String moduleType;

    /**
     * 所属模块的记录id
     */
    private Integer recordId;

    /**
     * 是否删除(1=是；0=否)
     */
    private Integer isDelete;

    /**
     * 附件位置
     */
    private String location;

    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 排序
     */
    private Integer sortBy;

}
