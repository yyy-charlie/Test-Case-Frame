package com.testcase.frame.common.document.file;

import com.testcase.frame.common.bean.BusFile;
import com.testcase.frame.common.constant.ResultCodeEnum;
import com.testcase.frame.common.document.IFileUtils;
import com.testcase.frame.common.exception.base.BusinessException;
import com.testcase.frame.common.util.ToolUtils;
import com.testcase.frame.common.util.ZipUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class AbsFileUtils implements IFileUtils {

    private static final Logger logger = LogManager.getLogger();

    public static final String  EXCEL_SUFFIX = ".xlsx";

    public static final String  ZIP_SUFFIX = ".zip";

    public static final String  SLASH = "/";

    public static final String BAR = "-";

    @Override
    public String createExcelFileByWorkbook(SXSSFWorkbook sxssfWorkbook, String fileName, String filePath) {

        String absolutePath = filePath + SLASH + fileName;

        ZipUtils.createTheDefaultDirs(filePath);

        this.buildExcelFile(sxssfWorkbook,absolutePath);

        return absolutePath;
    }

    @Override
    public String createZipFile(File file, String fileName, String filePath) {

        String  absolutePath = filePath + SLASH + fileName;

        List<File> files = new ArrayList<>();files.add(file);

        ZipUtils.zipFiles(files,new File(absolutePath));

        return absolutePath;
    }


    @Override
    public List<BusFile> saveFileOnDisk(List<FileItem> FileItems, String path){

        List<BusFile> busFiles = new ArrayList<>();

        for (FileItem fileItem : FileItems) {

            UUID randomUUID = UUID.randomUUID();

            String fileName = this.makeFileName(FilenameUtils.getName(fileItem.getName()),randomUUID);

            String filePath = this.makeHashFilePath(path,randomUUID) + SLASH + fileName;

            try {

                this.bufferReadAndWrite(fileItem.getInputStream(),new FileOutputStream(new File(
                        filePath)),8192);

                busFiles.add(new BusFile(fileName,FilenameUtils.getName(fileItem.getName()),fileItem.getContentType(),filePath));

            } catch (IOException e) {
                logger.error("保存文件异常");
                throw new BusinessException(ResultCodeEnum.UPLOAD_FILE_FAILURE);
            }finally {
                fileItem.delete();
            }
        }

        return busFiles;
    }


    @Override
    public List<BusFile>  uploadFile(List<MultipartFile> fileList, String fileUploadPath) {

        List<BusFile> busFiles = new ArrayList<>();

        for (MultipartFile item : fileList) {

            try {

                String suffix =item.getOriginalFilename().substring(item.getOriginalFilename().lastIndexOf(".") + 1);

             /*   //设置允许上传文件类型
                String suffixList = "jpg,png,ico,bmp,jpeg";

                if(suffixList.contains(suffix.trim().toLowerCase()))"")*/

                UUID randomUUID = UUID.randomUUID();

                String fileName = this.makeFileName(FilenameUtils.getName(item.getOriginalFilename()),randomUUID);

                String filePath = this.makeHashFilePath(fileUploadPath,randomUUID) + SLASH + fileName;

                File savedFile = new File(filePath);

                item.transferTo(savedFile.getAbsoluteFile());

                busFiles.add(new BusFile(fileName,FilenameUtils.getName(item.getOriginalFilename()),item.getContentType(),filePath));
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("保存文件异常" + e );
                throw new BusinessException(ResultCodeEnum.UPLOAD_FILE_FAILURE);
            }
        }

        return busFiles;
    }

    @Override
    public  void downAndDelFile(HttpServletResponse response, String path , String fileName){

        try {

            this.downFile(response,path,fileName);

        } catch (IOException e) {

            logger.error("下载文件异常" + e );
            throw new BusinessException(ResultCodeEnum.DOWNLOAD_FILE_FAILURE);
        }finally{

            this.deleteFileByPath(new File(path));
        }
    }

    @Override
    public  void downFile(HttpServletResponse response, String path , String fileName) throws IOException {

        File file = this.createDefaultFile(path);

        response.setContentType("application/ostet-stream");
        response.setHeader("Content-disposition", "attachment;filename="
                        + URLEncoder.encode(fileName, "UTF-8"));

        this.bufferReadAndWrite(new FileInputStream(file),response.getOutputStream(),8192);
    }

    @Override
    public  String  mkdirsByDate(String  path,Date date){

        String filePath = path + new SimpleDateFormat("/yyyy/MM/dd").format(date);;

        File f = new File(filePath);

        if(!f.exists()) {
            f.mkdirs();
        }

        return filePath;
    }

    public static void bufferReadAndWrite(InputStream ios,OutputStream ous,int index) throws IOException{

        BufferedInputStream bins = new BufferedInputStream(ios);
        BufferedOutputStream bouts = new BufferedOutputStream(ous);

        int len = 0;

        byte[] buffer = new byte[index];

        while ((len = bins.read(buffer, 0, index)) != -1) {

            bouts.write(buffer, 0, len);
            bouts.flush();
        }

         ToolUtils.closeIO(bins,bouts);
    }

    @Override
    public boolean deleteFileByPath(File file) {

        String[] files = null;

        if(file != null){

            files = file.list();
        }

        if(file.isDirectory()){

            for(int i  =  0;i  <  files.length; i ++){

                boolean result = this.deleteFileByPath(new File(file,files[i]));

                logger.debug("删除结果:" + result);
            }
        }

        return file.delete();
    }

    public String makeHashFilePath(String path ,UUID randomUUID){

        StringBuilder filepath = new StringBuilder();

        filepath.append(path);

        int hashUUID = randomUUID.hashCode();

        String hexUUID = Integer.toHexString(hashUUID);

        for (char c : hexUUID.toCharArray()) {

            filepath.append( "/" + c);
        }

        ZipUtils.createTheDefaultDirs(filepath.toString());

        return filepath.toString();
    }

    public String makeFileName(String clientName,UUID randomUUID){

        String filename = clientName;

        if (clientName.contains("\\")) {

            filename = clientName.substring(clientName.lastIndexOf("\\")).substring(1);
        }

        return randomUUID.toString() + filename;
    }



    public  File createDefaultFile(String filePath){

        File file = new File(filePath);

        if(!file.exists()) {

            try {
                file.createNewFile();
            } catch (IOException e) {

                logger.error("创建文件异常"+ filePath + e );
                throw new BusinessException(ResultCodeEnum.BUILD_EXCEL_FAILURE);
            }
        }

        return file;
    }


    public void  buildExcelFile(SXSSFWorkbook sxssfWorkbook, String pathName){


        BufferedOutputStream outFile = null;

        try {

            outFile = new BufferedOutputStream(new FileOutputStream(new File(pathName)), 2048);

            sxssfWorkbook.write(outFile);
            outFile.flush();

        } catch (IOException e) {
            logger.error("生成excel文件异常"+ pathName + e );
            throw new BusinessException(ResultCodeEnum.BUILD_EXCEL_FAILURE);
        }finally {
            ToolUtils.closeIO(outFile);
            sxssfWorkbook.dispose();
        }
    }

}
