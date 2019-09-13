package com.poi.excel.poi_excel.util;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author: Elvis
 * @Description: 通用操作工具类
 * @Date: 2019/9/13 22:37
 */
public class GeneralOperationUtil {

    private static final Logger log= LoggerFactory.getLogger(GeneralOperationUtil.class);

    /**
     * 通用下载附件
     * @param response
     * @param is
     * @param fileName
     */
    public static void downnloadFile(HttpServletResponse response, InputStream is, String fileName) {

        if(is == null || Strings.isNullOrEmpty(fileName)){
            return;
        }

        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            os = response.getOutputStream();
            bos = new BufferedOutputStream(os);
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"iso-8859-1"));
            byte [] buffer = new byte[1024];
            int len = bis.read(buffer);
            while (len != -1) {
                bos.write(buffer, 0, len);
                len = bis.read();
            }

            bos.flush();
        } catch (IOException e) {
            log.error("下载附件失败,{}", e.getMessage());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
