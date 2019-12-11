package com.testcase.frame.common.document;


import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IExportUtils {

    /**
     *  导出（包括对象里封装的List集合）
     * @param title  标题
     * @param colFieldName   属性名
     * @param list 数据源
     * @param objFieldName 属性名
     * @param columnWidth 列宽
     * @param response 响应
     * @param compress 是否压缩
     * @param <T>
     */
    public <T> void exportExcelFile(String title, String colFieldName, List<T> list, String objFieldName,
                                    short columnWidth, HttpServletResponse response, boolean compress);


    public <T> void exportSimpleExcelFile(String title, List<T> list, short columnWidth, HttpServletResponse response,
                                          boolean merged, boolean compress);
    /**
     *
     * @param title 标题
     * @param list 数据源
     * @param columnWidth 列宽
     * @param response 响应
     * @param compress 是否压缩
     * @param <T>
     */
    public <T> void exportExcelFile(String title, List<T> list, short columnWidth, HttpServletResponse response,
                                    boolean compress);

    /**
     *
     * @param fileName
     * @param response
     */
    public void sendResponseErrorStatus(String fileName, HttpServletResponse response);

    /**
     *
     * @param sxssfWorkbook
     * @param fileName
     * @param response
     * @param compress
     */
    public void  sendResponseByWorkbook(SXSSFWorkbook sxssfWorkbook, String fileName, HttpServletResponse response,
                                        boolean compress);

}
