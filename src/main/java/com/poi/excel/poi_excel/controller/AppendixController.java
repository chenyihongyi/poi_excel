package com.poi.excel.poi_excel.controller;

import com.google.common.base.Strings;
import com.poi.excel.poi_excel.dto.AppendixDto;
import com.poi.excel.poi_excel.entity.Appendix;
import com.poi.excel.poi_excel.enums.StatusCode;
import com.poi.excel.poi_excel.mapper.AppendixMapper;
import com.poi.excel.poi_excel.request.AppendixReuest;
import com.poi.excel.poi_excel.response.BaseResponse;
import com.poi.excel.poi_excel.service.AppendixService;
import com.poi.excel.poi_excel.util.GeneralOperationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;

/**
 * @Author: Elvis
 * @Description: 附件上传管理
 * @Date: 2019/9/13 16:07
 */
@RestController
public class AppendixController {

    private static final Logger log = LoggerFactory.getLogger(AppendixController.class);

    private static final String prefix = "appendix";

    @Autowired
    private AppendixService appendixService;

    @Autowired
    private AppendixMapper appendixMapper;

    @Autowired
    private Environment env;

    /**
     * 上传附件
     * @return
     */
    @RequestMapping(value = prefix+"/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public BaseResponse upload(MultipartHttpServletRequest request){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            String moduleType = request.getParameter("moduleType");
            if(Strings.isNullOrEmpty(moduleType)){
                return new BaseResponse(StatusCode.Invalid_Params);
            }
            MultipartFile file = request.getFile("fileName");
            if(file == null){
                return new BaseResponse(StatusCode.Invalid_Params);
            }
            AppendixDto dto = new AppendixDto();
            dto.setModuleType(moduleType);

            String recordId = request.getParameter("recordId");

            //TODO 通用上传服务
            final String location = appendixService.uploadFile(file, dto);
            log.info("该附件最终上传位置:{}", location);

            //TODO 保存上传附件记录
            dto.setLocation(location);
            Integer id = appendixService.saveRecord(file, dto);
            response.setData(id);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail);
            log.error("上传附件失败：{}", e.getMessage());
        }
        return response;
    }

    /**
     * 更新附件所属模块
     * @param appendixReuest
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = prefix+"/module/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse updateModuleAppendix(@RequestBody @Validated AppendixReuest appendixReuest, BindingResult bindingResult) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            if(bindingResult.hasErrors()){
                return new BaseResponse(StatusCode.Fail);
            }
            final Integer recordId = appendixReuest.getRecordId();
            String[] appendixIds = StringUtils.split(appendixReuest.getAppendixIds(), ",");
            Appendix a;
            for (String aId : appendixIds) {
                try{
                    a = appendixMapper.selectByPrimaryKey(Integer.valueOf(Integer.valueOf(aId)));
                    a.setRecordId(recordId);
                    if(a !=null){
                        appendixMapper.updateByPrimaryKeySelective(a);
                    }
                }catch (Exception e){
                    log.error("更新附件所属模块发生异常:当前附件id={}", aId);
                }

            }
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail);
            log.error("更新附件所属模块失败:{}", e.getMessage());
        }
        return response;
    }

    /**
     * 下载附件
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = prefix + "/download/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String downloadAppendix(@PathVariable Integer id, HttpServletResponse response) throws Exception {

        if (id == null || id <= 0) {
            return null;
        }
        try {
            Appendix appendix = appendixMapper.selectByPrimaryKey(id);
            // todo 上传文件服务实战三27.16
            if (appendix != null) {
                String fileLocation = env.getProperty("file.upload.root.url") + appendix.getLocation();
                GeneralOperationUtil.downnloadFile(response, new FileInputStream(fileLocation), appendix.getName());
                return appendix.getName();
            }

        } catch (Exception e) {
            log.error("下载附件失败,{}", e.getMessage());
        }
        return null;
    }


















}
