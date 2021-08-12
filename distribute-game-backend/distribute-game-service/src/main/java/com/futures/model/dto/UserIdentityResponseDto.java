package com.futures.model.dto;

import com.futures.model.entity.UserIdentity;
import com.futures.model.entity.UserPayment;
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