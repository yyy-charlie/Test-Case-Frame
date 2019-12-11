package com.testcase.frame.common.document;

import com.testcase.frame.common.bean.BusFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public interface IFileUtils {

    /**
     *
     * @param sxssfWorkbook 工作簿
     * @param fileName 文件名
     * @param filePath 文件存储路径
     * @return  文件存储路径(包含文件名)
     */
    public String createExcelFileByWorkbook(SXSSFWorkbook sxssfWorkbook, String fileName, String filePath);

    /**
     *
     * @param file  工作簿
     * @param fileName 文件名
     * @param filePath 文件存储路径
     * @return  文件存储路径(包含文件名)
     */
    public String createZipFile(File file, String fileName, String filePath);


    public List<BusFile> saveFileOnDisk(List<FileItem> FileItems, String path);

    public List<BusFile> uploadFile(List<MultipartFile> fileList, String path);

    public void downFile(HttpServletResponse response, String path, String fileName) throws IOException;

    /**
     *  下载且删除文件
     * @param response
     * @param path
     * @param fileName
     */
    public void downAndDelFile(HttpServletResponse response, String path, String fileName);

    /**
     *  删除文件 或 文件夹下的所有文件
     * @param file
     * @return
     */
    public boolean deleteFileByPath(File file);

    public  String  mkdirsByDate(String path, Date date);

    public List<File> getFilesExistsByDate(String serverPath, String uniquePrefix, String uniqueSuffix, String startTime,
                                           String endTime, String fileSuffix);

}
