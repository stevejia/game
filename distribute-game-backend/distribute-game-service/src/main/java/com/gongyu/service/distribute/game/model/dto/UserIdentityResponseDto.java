package com.gongyu.service.distribute.game.model.dto;

import com.gongyu.service.distribute.game.model.entity.UserIdentity;
import com.gongyu.service.distribute.game.model.entity.UserPayment;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel
public class UserIdentityResponseDto {

    private UserIdentity userIdentity;

    private List<UserPayment> userPaymentList;

}