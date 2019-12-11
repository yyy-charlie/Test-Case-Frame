package com.testcase.frame.common.document.workbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.testcase.frame.common.annotation.AttrName;
import com.testcase.frame.common.annotation.Ignore;
import com.testcase.frame.common.document.IWorkbookUtils;
import com.testcase.frame.common.util.ReflectUtils;
import com.testcase.frame.common.util.ToolUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.lang.reflect.Field;
import java.util.List;

import static com.testcase.frame.common.constant.Constants.*;


public abstract class AbsWorkbookUtils implements IWorkbookUtils {

    private static final Logger logger = LogManager.getLogger();

    private CellStyle topTitleStyle ;

    private  CellStyle columnsStyle;

    private  CellStyle bodyStyle;

    @Override
    public <T> SXSSFWorkbook buildWorkbook(String title, List<T> list,short columnWidth,String colFieldName,
                                           String objFieldName) {

        SXSSFWorkbook workbook = this.createTheDefaultSXSSFWorkbook();

        this.buildCellStyle(workbook);

        if(ToolUtils.judgeList(list)){

            Sheet sheet  =  this.createTheDefaultSXSSFSheet(workbook,title,columnWidth);

            Row row  = sheet.createRow(ROW_TITLE);

            int size =  this.buildSheetContent(sheet,list.get(0),columnsStyle,bodyStyle,colFieldName,list,objFieldName);

            AbsWorkbookUtils.createDefaultTitleSXSSFRow(size,title,topTitleStyle,row,sheet);
        }

        return workbook;
    }

    @Override
    public <T> SXSSFWorkbook buildSimpleWorkbook(String title, List<T> list, short columnWidth, boolean merged) {

        SXSSFWorkbook workbook = this.createTheDefaultSXSSFWorkbook();

        this.buildCellStyle(workbook);

        Sheet sheet  =  this.createTheDefaultSXSSFSheet(workbook,title,columnWidth);

        Row row  = sheet.createRow(ROW_TITLE);

        int size =  this.buildSimpleSheetContent(sheet,list.get(0),columnsStyle,bodyStyle,list,merged);

        AbsWorkbookUtils.createDefaultTitleSXSSFRow(size,title,topTitleStyle,row,sheet);

        return workbook;
    }

    /*---------------------------------------- protected ---------------------------------------------------*/



    /* ---------------------------------------------simple-------------------------------------------------------*/

    public <T> int buildSimpleSheetContent(Sheet sheet, T t, CellStyle columnsStyle, CellStyle bodyStyle, List<T> list,
                                           boolean merged) {

        Row row  = this.getLastRowBySheet(sheet);

        int size = this.setSimpleSXSSFCellColumns(columnsStyle,t,row);

        this.setSimpleSXSSFCellTxtValue(list,sheet,bodyStyle,merged);

        return size;
    }

    public <T> int setSimpleSXSSFCellColumns(CellStyle columnsStyle, T t, Row row) {

        Field[] fis =  t.getClass().getDeclaredFields();

        int size = 0;

        for (int i = 0 ; i < fis.length; i++) {

            if (fis[i].isAnnotationPresent(Ignore.class)) {

                continue;
            }

            fis[i].setAccessible(true);

            size += this.setSimpleColumnsCellValue(fis[i],row,columnsStyle);
        }

        return size;
    }

    public <T> int  setSimpleColumnsCellValue(Field field ,Row row ,CellStyle columnsStyle){

        int size = 0;

        int cellIndex = this.getLastCellIndexByRow(row);

        if(field.isAnnotationPresent(AttrName.class)){

            AttrName attributeName = field.getAnnotation(AttrName.class);

            Cell cell = row.createCell(cellIndex);
            cell.setCellStyle(columnsStyle);
            cell.setCellValue(attributeName.value());size ++;
        }

        return size;
    }

    public <T> void setSimpleSXSSFCellTxtValue(List<T> list,Sheet sheet,CellStyle bodyStyle,boolean merged) {

        int offset = this.getLastRowIndexBySheet(sheet) ;

        for (int i = 0; i < list.size(); i++) {

            Row  row = sheet.createRow(i + offset);

            Object obj = list.get(i);
            Field[] fis =  obj.getClass().getDeclaredFields();

            this.setSimpleCellValueByFields(obj,fis,row,bodyStyle,merged);
        }

    }


    public  void  setSimpleCellValueByFields(Object obj,Field[] fis,Row row,CellStyle bodyStyle,boolean merged){

        for (int k = 0 ; k < fis.length; k++) {

            if (fis[k].isAnnotationPresent(Ignore.class)) {

                continue;
            }

            fis[k].setAccessible(true);

            String fieldType = fis[k].getType().toString();

            Object value  =  ReflectUtils.getFieldValue(fis[k].getName(),obj);

            this.setSimpleCellTextValue(fieldType,row,bodyStyle,value,merged);
        }
    }


    public  void setSimpleCellTextValue(String fieldType,Row row,CellStyle bodyStyle,Object value,boolean merged){

        int cellIndex = this.getLastCellIndexByRow(row);

        if(JSON_LIST_FIELD_TYPE.equals(fieldType)){

            this.setSimpleCellTextValueByList(row,bodyStyle,cellIndex,value,merged);
        }else{
            Cell cell = row.createCell(cellIndex);

            this.setCellTextValue(value,cell,bodyStyle);
        }
    }

    public  int setSimpleCellTextValueByList(Row row,CellStyle bodyStyle,int cellIndex,Object value,boolean merged){

        if(ToolUtils.isNull(value)) return 0;

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(value));

        boolean flag = ToolUtils.judgeList(jsonArray);

        flag = merged && flag ? this.setSimpleCellText2Merged(jsonArray,row,cellIndex,bodyStyle)
                : this.setSimpleCellText(jsonArray,row,cellIndex,bodyStyle);

        return jsonArray.size();
    }

    public boolean setSimpleCellText2Merged(JSONArray jsonArray,Row row,int cellIndex,CellStyle bodyStyle){

        Cell cell = row.createCell(cellIndex);

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < jsonArray.size(); i++){

            str.append(jsonArray.getString(i) + WRAP);
        }

        this.setSimpleCellTextValue(str.toString(),cell,bodyStyle);

        return true;
    }

    public boolean setSimpleCellText(JSONArray jsonArray,Row row,int cellIndex,CellStyle bodyStyle){

        for (int i = 0; i < jsonArray.size(); i++) {

            Cell cell = row.createCell(cellIndex + i);

            Object itemValue = jsonArray.getString(i);

            this.setCellTextValue(itemValue, cell, bodyStyle);
        }

        return true;
    }


    private  void setSimpleCellTextValue(String str,Cell it,CellStyle bodyStyle){

        str = ToolUtils.isEmpty(str) ? BAR: str;
        HSSFRichTextString text = new HSSFRichTextString(str);
        it.setCellStyle(bodyStyle);
        it.setCellValue(text);
    }



    /* -------------------------------------非 null -----------------------------------------------------*/


    public <T> int buildSheetContent(Sheet sheet, T t, CellStyle columnsStyle, CellStyle bodyStyle,
                                     String colFieldName, List<T> list, String objFieldName) {

        Row row  = this.getLastRowBySheet(sheet);

        int size = this.setSXSSFCellColumns(columnsStyle,t,colFieldName,row);

        this.setSXSSFCellTxtValue(list,sheet,objFieldName,bodyStyle);

        return size;
    }

    public <T> int setSXSSFCellColumns(CellStyle columnsStyle, T t, String colFieldName, Row row) {

        Field[] fis =  t.getClass().getDeclaredFields();

        int size = 0;

        for (int i = 0 ; i < fis.length; i++) {

            if (fis[i].isAnnotationPresent(Ignore.class)) {

                continue;
            }

            fis[i].setAccessible(true);

            size += this.setColumnsCellValue(t,fis[i],colFieldName,row,columnsStyle);
        }

        return size;
    }


    public <T> int  setColumnsCellValue(T t,Field field ,String colFieldName,Row row ,
                                        CellStyle columnsStyle){

        int size = 0;

        int cellIndex = this.getLastCellIndexByRow(row);

        String fieldType = field.getType().toString();

        if(field.isAnnotationPresent(AttrName.class)){

            AttrName attributeName = field.getAnnotation(AttrName.class);

            Cell cell = row.createCell(cellIndex);
            cell.setCellStyle(columnsStyle);
            cell.setCellValue(attributeName.value());size ++;

        }else if(JSON_LIST_FIELD_TYPE.equals(fieldType)){

            Object value  =  ReflectUtils.getFieldValue(field.getName(),t);

            size += this.setCellTextValueByList(row,columnsStyle,cellIndex,colFieldName,value);
        }

        return size;
    }

    public <T> void setSXSSFCellTxtValue(List<T> list,Sheet sheet, String objFieldName,CellStyle bodyStyle) {

        int offset = this.getLastRowIndexBySheet(sheet) ;

        for (int i = 0; i < list.size(); i++) {

            Row  row = sheet.createRow(i + offset);

            Object obj = list.get(i);
            Field[] fis =  obj.getClass().getDeclaredFields();

            this.setCellValueByFields(obj,fis,row,bodyStyle,objFieldName);
        }

    }

    public  void  setCellValueByFields(Object obj,Field[] fis,Row row,CellStyle bodyStyle,String objFieldName){

        for (int k = 0 ; k < fis.length; k++) {

            if (fis[k].isAnnotationPresent(Ignore.class)) {

                continue;
            }

            fis[k].setAccessible(true);

            String fieldType = fis[k].getType().toString();

            Object value  =  ReflectUtils.getFieldValue(fis[k].getName(),obj);

            this.setCellTextValue(fieldType,row,bodyStyle,value,objFieldName);
        }
    }


    public  void setCellTextValue(String fieldType,Row row,CellStyle bodyStyle,Object value,String objFieldName){

        int cellIndex = this.getLastCellIndexByRow(row);

        if(JSON_LIST_FIELD_TYPE.equals(fieldType)){

            this.setCellTextValueByList(row,bodyStyle,cellIndex,objFieldName,value);
        }else{

            Cell cell = row.createCell(cellIndex);

            this.setCellTextValue(value,cell,bodyStyle);
        }
    }

    public  int setCellTextValueByList(Row row,CellStyle bodyStyle,int cellIndex,String argsFieldName,Object value){

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(value));

        if(ToolUtils.judgeList(jsonArray)){

            for (int i = 0; i < jsonArray.size(); i++){

                Cell cell = row.createCell(cellIndex + i);

                Object itemValue =  jsonArray.getJSONObject(i).get(argsFieldName);

                this.setCellTextValue(itemValue,cell,bodyStyle);
            }
        }

        return jsonArray.size();
    }

    private  void setCellTextValue(Object value,Cell it,CellStyle bodyStyle){

        String str = value.toString().equals("null") ? "":value.toString();

        if(ToolUtils.isStr2Double(str)){
            Double douValue = Double.parseDouble(str);
            it.setCellStyle(bodyStyle);
            it.setCellValue(douValue);
            return;
        }

        HSSFRichTextString text = new HSSFRichTextString(str);
        it.setCellStyle(bodyStyle);
        it.setCellValue(text);
    }

    /*-------------------------- Style ----------------------------------------*/
    @Override
    public  SXSSFWorkbook createTheDefaultSXSSFWorkbook() {

        int rowaccess = 1000;

        return new SXSSFWorkbook(rowaccess);
    }


    public static Sheet createTheDefaultSXSSFSheet(SXSSFWorkbook sxssfWorkbook, String title, short columnWidth) {

        Sheet sheet = sxssfWorkbook.createSheet(title);
        sheet.setDefaultColumnWidth(columnWidth);

        return sheet;
    }


    public static Row createDefaultTitleSXSSFRow(int columnSize, String title, CellStyle columnTopStyle, Row row,Sheet sheet) {

        Cell cellTiltle = row.createCell(TITLE_CELL);

        cellTiltle.setCellStyle(columnTopStyle);
        cellTiltle.setCellValue(title);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnSize - 1));

        return row;
    }


    public  static CellStyle createTopTitleStyle(SXSSFWorkbook workbook) {

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontHeightInPoints((short) 13);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("Courier New");

        style.setFont(font);

        AbsWorkbookUtils.buildBorderByCellStyle(style);

        return style;
    }

    public  static  CellStyle createColumnsStyle(SXSSFWorkbook workbook) {

        CellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setFillForegroundColor(HSSFColor.TAN.index);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        Font font =  workbook.createFont();

        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 13);
        cellStyle.setFont(font);

        AbsWorkbookUtils.buildBorderByCellStyle(cellStyle);

        return cellStyle;

    }


    public static CellStyle createBodyStyle(SXSSFWorkbook workbook) {

        CellStyle cellStyle = workbook.createCellStyle();

        XSSFFont font = (XSSFFont) workbook.createFont();

        font.setFontName("宋体");
        font.setFontHeight(10);
        cellStyle.setFont(font);

        AbsWorkbookUtils.buildBorderByCellStyle(cellStyle);

        return cellStyle;
    }

    @Override
    public Row getLastRowBySheet(Sheet sheet){

        int rowIndex = this.getLastRowIndexBySheet(sheet);

        return  sheet.createRow(rowIndex);
    }

    @Override
    public int getLastRowIndexBySheet(Sheet sheet) {

        int lastRowIndex = sheet.getLastRowNum();

        return lastRowIndex == -1 ? 0 :++lastRowIndex;
    }

    @Override
    public int getLastCellIndexByRow(Row row) {

        int lastCellNum = row.getLastCellNum();

        return lastCellNum == -1 ? ++lastCellNum : lastCellNum ;
    }

    private void  buildCellStyle(SXSSFWorkbook workbook ){

        this.topTitleStyle = AbsWorkbookUtils.createTopTitleStyle(workbook);

        this.columnsStyle = AbsWorkbookUtils.createColumnsStyle(workbook);

        this.bodyStyle = AbsWorkbookUtils.createBodyStyle(workbook);
    }

    private static void buildBorderByCellStyle(CellStyle cellStyle){

        cellStyle.setWrapText(true);

        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
    }
}
