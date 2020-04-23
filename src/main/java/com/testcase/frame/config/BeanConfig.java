package com.testcase.frame.config;

import com.hdst.bocontainer.mapperOperation.impl.MapperOperationServiceImpl;
import com.hdst.bocontainer.mapperOperation.intf.IMapperOperationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

//    @Bean(name = "IExportUtils")
//    public ExportUtils getExportUtils() {
//        return new ExportUtils();
//    }
//
//    @Bean(name="IFileUtils")
//    public FileUtils getFileUtils() { return new FileUtils(); }
//
//    @Bean(name="iWorkbookUtils")
//    public WorkbookUtils getWorkbookUtils() { return new WorkbookUtils(); }

    @Bean(name = "mapperOperationService")
    public IMapperOperationService getWorkbookUtils() {
        return new MapperOperationServiceImpl();
    }
}
