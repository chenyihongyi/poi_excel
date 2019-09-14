package com.poi.excel.poi_excel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: Elvis
 * @Description:配置跨域方式一
 * @Date: 2019/9/14 21:25
 */
@Configuration
public class CustomerMvcConfig extends WebMvcConfigurerAdapter {

    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("*");
    }



}
