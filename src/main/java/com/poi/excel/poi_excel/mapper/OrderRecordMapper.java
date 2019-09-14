package com.poi.excel.poi_excel.mapper;

import com.poi.excel.poi_excel.entity.Appendix;
import com.poi.excel.poi_excel.entity.OrderRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/14 1:46
 */
public interface OrderRecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderRecord record);

    int insertSelective(OrderRecord record);

    OrderRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRecord record);

    int updateByPrimaryKey(OrderRecord record);


}
