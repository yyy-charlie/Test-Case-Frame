package com.testcase.frame.common.document.file;

import com.testcase.frame.common.document.IFileUtils;
import com.testcase.frame.common.util.DateUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Component
public class FileUtils extends AbsFileUtils implements IFileUtils {


    @Override
    public List<File> getFilesExistsByDate(String serverPath, String uniquePrefix, String uniqueSuffix, String startTime,
                                          String endTime, String fileSuffix) {

        List<String> list = DateUtils.getAllDatesByDateRange(startTime,endTime);

        return  this.readSrcFilesByDateRange(list,serverPath,uniquePrefix,uniqueSuffix,fileSuffix);
    }

    private  List<File> readSrcFilesByDateRange(List<String> list,String serverPath,String uniquePrefix,
                                                String uniqueSuffix,String fileSuffix){

        List<File>  srcFiles = new ArrayList<>();

        for (String date : list){

            String path = SLASH + uniquePrefix + SLASH + date + SLASH + uniqueSuffix;

            String filePath = serverPath + path + date.replace('/','-')+ fileSuffix;

            File file  = new File(filePath);

            if(file.exists()){

                srcFiles.add(file);
            }
        }

        return srcFiles;
    }
}
