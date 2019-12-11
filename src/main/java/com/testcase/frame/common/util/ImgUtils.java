package com.testcase.frame.common.util;

import com.testcase.frame.common.document.file.AbsFileUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


@Component
public class ImgUtils {


    /*
     根据文件地址，获取输入流
     */

    public static FileInputStream getFileInputStream(File file){

        FileInputStream in = null;

        try {

            in = new FileInputStream(file);

        } catch (FileNotFoundException e) {

            return null;
        }

        return  in;
    }

    /**
     *  下载图片文件
     * @param filepath
     * @param response
     * @throws IOException  "image/jpeg"
     */
    public static void downFile(String filepath, HttpServletResponse response, String type) throws IOException {

        FileInputStream is = getFileInputStream(new File(filepath));

        if (is != null){

            response.setContentType(type);

            AbsFileUtils.bufferReadAndWrite(is, response.getOutputStream(),is.available());
        }
    }
}
