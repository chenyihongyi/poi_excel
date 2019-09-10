package com.poi.excel.poi_excel.service;

import com.google.common.collect.Maps;
import com.poi.excel.poi_excel.entity.Product;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/10 21:21
 */
@Service
public class ProductService {

    public List<Map<Integer, Object>> manageProductList(final List<Product> products) {
        List<Map<Integer, Object>> listMap = new LinkedList<Map<Integer, Object>>();
        Map<Integer, Object> rowMap;
        for(Product p: products){
            rowMap = Maps.newHashMap();
            rowMap.put(0, p.getName());
            rowMap.put(1, p.getUnit());
            rowMap.put(2, p.getPrice());
            rowMap.put(3, p.getStock());
            rowMap.put(4, p.getRemark());
            rowMap.put(5, p.getPurchaseDate());

            listMap.add(rowMap);
        }
        return listMap;
    }


}
