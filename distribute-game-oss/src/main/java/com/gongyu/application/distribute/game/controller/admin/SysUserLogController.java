package com.gongyu.application.distribute.game.controller.admin;

import com.gongyu.service.distribute.game.model.dto.UserLogExportExcel;
import com.gongyu.service.distribute.game.service.SysUserFpi;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.Pageable;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("sys/userLog")
@Api(tags = "系统管理")
@Slf4j
public class SysUserLogController {
    @Autowired
    private SysUserFpi sysUserFpi;

    @ApiOperation(value = "【操作日志】-板块列表", notes = "操作日志-板块列表",response = String.class)
    @PostMapping(value = "queryAdminLogModes")
    public BaseResponse queryAdminLogModes() {
        return BaseResponse.success(sysUserFpi.queryUserLogModes());
    }

    @ApiOperation(value = "【操作日志】-操作日志列表", notes = "操作日志-操作日志列表",response = SysUserLog.class)
    @PostMapping(value = "queryUserLog")
    public BaseResponse queryUserLog(Pageable page, @ApiParam(value = "操作人") String userName,
                                     @ApiParam(value = "板块") String modelName,
                                     @ApiParam(value = "参数") String argument,
                                     @ApiParam(value = "操作时间-开始") String startTime,
                                     @ApiParam(value = "操作时间-结束") String endTime) {
        return BaseResponse.success(sysUserFpi.querySysUserLog(page.getPage(), userName, modelName, argument, startTime, endTime));
    }

    @ApiOperation(value = "【操作日志】-导出操作日志报表", notes = "操作日志-导出操作日志报表（直接返回excel文件流）")
    @GetMapping(value = "exportUserLog",produces = {"application/vnd.ms-excel"})
    @SysUserLog(module = "操作日志", action = "导出报表")
    public void exportUserLog(@ApiParam(value = "操作人") String userName,
                               @ApiParam(value = "板块") String modelName,
                               @ApiParam(value = "参数") String argument,
                               @ApiParam(value = "操作时间-开始") String startTime,
                               @ApiParam(value = "操作时间-结束") String endTime,
                               HttpServletResponse response) {
        try{
            XSSFWorkbook workbook = new XSSFWorkbook();
            //生成sheet页
            Sheet sheet = workbook.createSheet("操作日志报表");
            //设置列宽
            UserLogExportExcel.setColumnWidth(sheet);
            //设置列头样式
            XSSFCellStyle greyStyle = UserLogExportExcel.buildGeryCell(workbook);
            //生成列头
            UserLogExportExcel.createHeader(sheet, greyStyle);
            int count = 1;
            List<com.gongyu.service.distribute.game.model.entity.SysUserLog> sysUserLogList = sysUserFpi.exportSysUserLog(userName, modelName, argument, startTime, endTime);
            for (com.gongyu.service.distribute.game.model.entity.SysUserLog sysUserLog : sysUserLogList) {
                Row row = sheet.createRow(count);
                row.createCell(UserLogExportExcel.ID).setCellValue(sysUserLog.getId());
                row.createCell(UserLogExportExcel.USER_NAME).setCellValue(sysUserLog.getUserName());
                row.createCell(UserLogExportExcel.OPERATION_TIME).setCellValue(DateFormatUtils.format(sysUserLog.getOperationTime(),"yyyy-MM-dd HH:mm:ss"));
                row.createCell(UserLogExportExcel.IP).setCellValue(sysUserLog.getIp());
                row.createCell(UserLogExportExcel.MODEL_NAME).setCellValue(sysUserLog.getModelName());
                row.createCell(UserLogExportExcel.MEMO).setCellValue(sysUserLog.getMemo());
                count++;
            }

            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(workbook.getSheetName(0) + ".xlsx", "UTF-8"));
            ServletOutputStream stream = response.getOutputStream();
            workbook.write(response.getOutputStream());
            stream.flush();
            response.flushBuffer();
        }catch (Exception e){
            log.error("exportUserLog error=" + e.getMessage(), e);
        }
    }
}
