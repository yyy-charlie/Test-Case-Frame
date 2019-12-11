package com.testcase.frame.common.document;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;

public interface IWorkbookUtils {


    public <T> SXSSFWorkbook buildWorkbook(String title, List<T> list, short columnWidth, String colFieldName,
                                           String objFieldName);

    public <T> SXSSFWorkbook buildSimpleWorkbook(String title, List<T> list, short columnWidth, boolean merged);

    public SXSSFWorkbook createTheDefaultSXSSFWorkbook();

    public Row getLastRowBySheet(Sheet sheet);

    public int getLastRowIndexBySheet(Sheet sheet);

    public int getLastCellIndexByRow(Row row);
}
