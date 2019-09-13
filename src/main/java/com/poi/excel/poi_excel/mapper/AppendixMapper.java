package com.poi.excel.poi_excel.mapper;

import com.poi.excel.poi_excel.entity.Appendix;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/13 16:02
 */
public interface AppendixMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Appendix record);

    int insertSelective(Appendix record);

    Appendix selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Appendix record);

    int updateByPrimaryKey(Appendix record);
}
