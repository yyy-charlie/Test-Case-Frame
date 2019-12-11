package com.testcase.frame.common.document.export;

import com.testcase.frame.common.constant.ResultCodeEnum;
import com.testcase.frame.common.document.IExportUtils;
import com.testcase.frame.common.document.IFileUtils;
import com.testcase.frame.common.document.IWorkbookUtils;
import com.testcase.frame.common.document.file.AbsFileUtils;
import com.testcase.frame.common.exception.base.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class AbsExportUtils implements IExportUtils {


    private static final Logger logger = LogManager.getLogger();

    private static final String PATH = "/mnt/upload";

    private static final String TEMP_PATH = PATH + "/temp";

    @Autowired
    private IWorkbookUtils iWorkbookUtils;

    @Autowired
    private IFileUtils iFileUtils;


    @Override
    public <T> void exportExcelFile(String title, List<T> list, short columnWidth, HttpServletResponse response,
                                    boolean compress) {

        this.exportSimpleExcelFile(title,list,columnWidth,response,compress,false);
    }

    @Override
    public <T> void exportSimpleExcelFile(String title, List<T> list, short columnWidth, HttpServletResponse response,
                                    boolean merged, boolean compress) {

        SXSSFWorkbook sxssfWorkbook = iWorkbookUtils.buildSimpleWorkbook(title, list,columnWidth,merged);

        this.sendResponseByWorkbook(sxssfWorkbook,title,response,compress);
    }

    @Override
    public <T> void exportExcelFile(String title, String colFieldName, List<T> list, String objFieldName,
                                    short columnWidth, HttpServletResponse response, boolean compress) {

        SXSSFWorkbook sxssfWorkbook = iWorkbookUtils.buildWorkbook(title, list,columnWidth, colFieldName,objFieldName);

        this.sendResponseByWorkbook(sxssfWorkbook,title,response,compress);
    }

    @Override
    public void sendResponseErrorStatus(String fileName ,HttpServletResponse response) {

        try {

            iFileUtils.downFile(response, TEMP_PATH  + AbsFileUtils.SLASH + fileName, fileName);

        } catch (IOException e) {
            logger.error("生成无数据文件异常"+ fileName + e );
            throw new BusinessException(ResultCodeEnum.BUILD_EXCEL_FAILURE);
        }
    }

    @Override
    public void sendResponseByWorkbook(SXSSFWorkbook sxssfWorkbook,String fileName,HttpServletResponse response,
                                       boolean compress) {

        String suffix = AbsFileUtils.EXCEL_SUFFIX;

        String path = iFileUtils.createExcelFileByWorkbook(sxssfWorkbook,fileName + suffix, TEMP_PATH);

        if(compress){

            suffix = AbsFileUtils.ZIP_SUFFIX;

            path = iFileUtils.createZipFile(new File(path),fileName + suffix ,TEMP_PATH);
        }

        iFileUtils.downAndDelFile(response, path, fileName + suffix);
    }
}
