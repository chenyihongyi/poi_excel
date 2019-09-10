package com.poi.excel.poi_excel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan(basePackages = "com.poi.excel.poi_excel.mapper")
@SpringBootApplication
public class PoiExcelApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PoiExcelApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(PoiExcelApplication.class, args);
	}

}
