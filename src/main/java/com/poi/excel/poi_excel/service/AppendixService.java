package com.poi.excel.poi_excel.service;


import com.poi.excel.poi_excel.dto.AppendixDto;
import com.poi.excel.poi_excel.entity.Appendix;
import com.poi.excel.poi_excel.mapper.AppendixMapper;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Elvis
 * @Description:
 * @Date: 2019/9/13 16:38
 */
@Service
public class AppendixService {

    private static final Logger log = LoggerFactory.getLogger(AppendixService.class);

    @Autowired
    private Environment env;

    @Autowired
    private AppendixMapper appendixMapper;

    /**
     * 通用上传附件服务
     * @param file
     * @param dto
     * @return
     * @throws Exception
     */
    public String uploadFile(MultipartFile file, AppendixDto dto) throws Exception {
        if (file == null) {
            throw new RuntimeException("附件为空!");
        }
        String fileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(fileName, fileName.lastIndexOf("."));

        //定义最终附件存储目录
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateDirectory=dateFormat.format(new Date());
        String rootUrl = env.getProperty("file.upload.root.url") + File.separator + dto.getModuleType() + File.separator + dateDirectory + File.separator;
        File rootFile = new File(rootUrl);
        if(!rootFile.exists()){
            rootFile.mkdirs();
        }

        //构造最终附件名
        dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String destFileName = dateFormat.format(new Date()) + suffix;
        File destFile = new File(rootUrl + File.separator + destFileName);

        file.transferTo(destFile);
        //  String finalLocation = rootUrl.replace(env.getProperty("file.upload.root.url"), "") + destFileName;
        String location = File.separator + dto.getModuleType() + File.separator + dateDirectory + File.separator + destFileName;
        return location;
    }

    /**
     * 保存上传附件记录
     * @param file
     * @param dto
     */
    public Integer saveRecord(MultipartFile file, AppendixDto dto){
        Appendix entity = new Appendix();
        BeanUtils.copyProperties(dto, entity);

        entity.setName(file.getOriginalFilename());
        entity.setSize(file.getSize());
        entity.setCreateTime(new Date());
        entity.setIsDelete(0);

        appendixMapper.insert(entity);
        return entity.getId();
    }














}
