package com.poi.excel.poi_excel.mapper;

import com.poi.excel.poi_excel.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/10 16:50
 */
public interface ProductMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectAll(@Param("name") String name);

    int insertBatch(List<Product> products);
}
