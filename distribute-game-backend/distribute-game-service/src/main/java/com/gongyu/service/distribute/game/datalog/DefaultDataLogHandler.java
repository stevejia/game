package com.gongyu.service.distribute.game.datalog;//package com.gongyu.service.tax.datalog;
//
//import com.gongyu.service.tax.common.model.entity.BusinessChangeRecord;
//import com.gongyu.service.tax.common.service.BusinessChangeRecordService;
//import com.gongyu.snowcloud.framework.util.SpringBeanUtil;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
///**
// * 数据日志处理实现（在要拦截的SpringBoot工程集成）
// *
// * @author chenyong
// * @date 2019/12/31
// */
//@Service
//@AllArgsConstructor
//public class DefaultDataLogHandler implements DataLogHandler {
//
//    @SneakyThrows
//    @Override
//    public void insertHandler(InsertInfo insertInfo) {
////        System.out.println("插入：" + JSONObject.toJSONString(insertInfo.getInsertObj()));
//    }
//
//    @SneakyThrows
//    @Override
//    public void updateHandler(UpdateInfo updateInfo) {
//        List<CompareResult> cr = updateInfo.getCompareResult();
//        StringBuilder sb = new StringBuilder();
//        sb.append("更新 ");
//        sb.append(updateInfo.getBasicInfo().getTbName());
//        sb.append(" 表，更新如下字段：");
//        StringBuilder oldValue = new StringBuilder();
//        StringBuilder newValue = new StringBuilder();
//        cr.forEach(r -> {
//            if(cr.size() - 1 == cr.indexOf(r)){
//                oldValue.append(r.getFieldComment() + "：" + r.getOldValue());
//                newValue.append(r.getFieldComment() + "：" + r.getNewValue());
//                sb.append(r.getFieldComment() + "：" + r.getOldValue() + "-->" + r.getNewValue());
//            }else {
//                oldValue.append(r.getFieldComment() + "：" + r.getOldValue()+ "，");
//                newValue.append(r.getFieldComment() + "：" + r.getNewValue()+ "，");
//                sb.append(r.getFieldComment() + "：" + r.getOldValue() + "-->" + r.getNewValue() + "，");
//            }
//        });
//        System.out.println(sb.toString());
//
//        BusinessChangeRecord businessChangeRecord = new BusinessChangeRecord();
//        businessChangeRecord.setItemName(updateInfo.getBasicInfo().getTbName());
//        businessChangeRecord.setItemId(updateInfo.getBasicInfo().getPkValue());
//        businessChangeRecord.setOldValue(oldValue.toString());
//        businessChangeRecord.setNewValue(newValue.toString());
//        businessChangeRecord.setRemark(updateInfo.getBasicInfo().getTbComment());
//        BusinessChangeRecordService businessChangeRecordService = SpringBeanUtil.getBean(BusinessChangeRecordService.class);
//        businessChangeRecordService.save(businessChangeRecord);
//    }
//
//    @SneakyThrows
//    @Override
//    public void deleteHandler(DeleteInfo deleteInfo) {
////        System.out.println("删除:" + JSONObject.toJSONString(deleteInfo.getDeleteObj()));
//    }
//}