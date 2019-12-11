package com.testcase.frame.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {


    private static final Logger logger = LogManager.getLogger();


    /**
     * 将多个文件打包成zip文件
     */
    public static void zipFiles(List<File> srcfile, File zipfile) {

        byte[] buf = new byte[1024];

        ZipOutputStream out = null;

        FileInputStream in = null;

        try {

            out = new ZipOutputStream(new FileOutputStream(zipfile));

            for (int i = 0; i < srcfile.size(); i++) {

                File file = srcfile.get(i);

                in = ImgUtils.getFileInputStream(file);

                if(!ToolUtils.isNull(in)){

                    out.putNextEntry(new ZipEntry(file.getName()));

                    int len;

                    while ((len = in.read(buf)) > 0) {

                        out.write(buf, 0, len);
                    }
                    out.closeEntry();
                    in.close();
                }
            }
        } catch (IOException e) {

        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File createTheDefaultDirs(String  path){

        File file = new File(path);

        if (!file.exists()) {

            file.mkdirs();
        }
        return file;
    }
}
