package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.UserPayment;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/18 18:26
 */
public interface MyService {


   /**
    * 我的基本信息
    * @param userId
    * @return
    */
   BaseResponse getMyBaseData(Long userId);

   /**
    * 累计收益
    * @return
    */
   BaseResponse accumIncome(IPage page,Long userId,Integer type);

   /**
    * 出售推广收益
    * @return
    */
   BaseResponse sellIncome(SellIncomeReqDto param,Long userId);

   /**
    * 领养记录
    * @param userId
    * @return
    */
   BaseResponse adoptRecord(IPage page,AdoptReqDto param, Long userId);

   /**
    * 申诉提交
    * @param param
    * @return
    */
   BaseResponse appeal(AppealReqDto param);

   /**
    * 预约记录
    * @param param
    * @return
    */
   BaseResponse reserve(IPage page,Long userId);

   /**
    * 转让记录
    * @param param
    * @param userId
    * @return
    */
   BaseResponse transf(IPage page,TransfReqDto param,Long userId);

   /**
    * 收款方式
    * @param userId
    * @return
    */
   BaseResponse collectList(Long userId);

   /**
    * 添加修改收款方式
    * @param payment
    * @return
    */
   BaseResponse addUpdate(UserPayment payment);

   /**
    * 实名认证
    * @param param
    * @return
    */
   BaseResponse auth(AuthReqDto param);

   /**
    * 生成我的推广码url
    * @param userId
    * @return
    */
   BaseResponse recomCode(Long userId);

   /**
    * 我的团队
    * @param userId
    * @return
    */
   BaseResponse myTeam(Long userId);

   /**
    * 检查是否拥有合法收款方式
    * @return
    */
   BaseResponse checkedPayment(Long userId);
   BaseResponse checkedProAuth(Long userId);
}
