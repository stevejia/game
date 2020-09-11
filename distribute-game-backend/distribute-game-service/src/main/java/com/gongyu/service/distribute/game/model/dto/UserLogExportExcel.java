package com.gongyu.service.distribute.game.model.dto;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class UserLogExportExcel {
    /** 表头 */
    public static final List<String> EXCEL_HEADER  = new ArrayList(){
        {
            add("日志ID");
            add("操作人");
            add("操作时间");
            add("IP地址");
            add("板块");
            add("操作");
        }
    };

    /** 日志ID */
    public static final int          ID        = 0;
    /** 操作人 */
    public static final int          USER_NAME    = 1;
    /** 操作时间 */
    public static final int          OPERATION_TIME    = 2;
    /** IP地址 */
    public static final int          IP     = 3;
    /** 板块 */
    public static final int          MODEL_NAME     = 4;
    /** 操作 */
    public static final int          MEMO     = 5;

    public static final int WIDTH_UNIT = 256;

    /** 创建表头 */
    public static void createHeader(Sheet sheet, XSSFCellStyle style) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < EXCEL_HEADER.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(EXCEL_HEADER.get(i));
        }
    }

    public static void setColumnWidth(Sheet sheet) {
        sheet.setColumnWidth(ID, 20 * WIDTH_UNIT);
        sheet.setColumnWidth(USER_NAME, 20 * WIDTH_UNIT);
        sheet.setColumnWidth(OPERATION_TIME, 20 * WIDTH_UNIT);
        sheet.setColumnWidth(IP, 20 * WIDTH_UNIT);
        sheet.setColumnWidth(MODEL_NAME, 20 * WIDTH_UNIT);
        sheet.setColumnWidth(MEMO, 30 * WIDTH_UNIT);
    }

    public static XSSFCellStyle buildGeryCell(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        return style;
    }
}
