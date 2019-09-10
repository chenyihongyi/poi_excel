package com.poi.excel.poi_excel.response;

import com.poi.excel.poi_excel.enums.StatusCode;
import org.apache.poi.ss.formula.functions.T;

/**
 * @Author: Elvis
 * @Description: 通用的响应模型
 * @Date: 2019/9/10 17:26
 */

public class BaseResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResponse(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public BaseResponse(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(T data, StatusCode statusCode) {
        this.code = code;
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
