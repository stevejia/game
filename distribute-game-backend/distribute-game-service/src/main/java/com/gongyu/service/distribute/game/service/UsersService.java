package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.enums.IncomeTypeEnum;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.PigReservation;
import com.gongyu.service.distribute.game.model.entity.Users;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UsersService extends CrudService<Users> {

    IPage<Users> queryUsers(IPage<Users> page, String mobile, Long id);

    Integer saveUsers(UsersSaveDto usersSaveDto);

    void modifyUsersFirstLeader(UsersModifyDto usersModifyDto);

    void modifyUsersInfo(UsersModifyDto usersModifyDto);

    /**
     * 修改积分
     *
     * @param userId
     * @param type      1推广收益 2积分
     * @param direction 1增加积分 2减少积分
     * @param score
     */
    void modifyAccount(Integer userId, int type, BigDecimal score, int direction, String remark, IncomeTypeEnum incomeTypeEnum);

    /**
     * 修改积分
     *
     * @param userId
     * @param direction 1增加 2减少
     * @param score
     */
    boolean modifyPayPoints(Integer userId, int score, int direction, String remark, IncomeTypeEnum incomeTypeEnum);

    /**
     * 修改推广收益
     *
     * @param userId
     * @param direction 1增加 2减少
     * @param score
     */
    boolean modifyUserMoney(Integer userId, BigDecimal score, int direction, String remark, IncomeTypeEnum incomeTypeEnum);

    /**
     * 查询树结构
     *
     * @param userId
     * @return
     */
    List<UsersTreeResponseDto> queryTreeList(Long userId, String type);

    /**
     * 重新构建用户积分
     *
     * @return
     */
    List<Users> convertUserPoints(List<PigReservation> reservats);

    /**
     * 登录
     *
     * @return
     */
    MemberLoginResponseDto login(String mobile, String password,
                                 String codeKey,
                                 String codeValue,
                                 String loginIp);

    /**
     * 写登录信息
     *
     * @return
     */
    MemberLoginResponseDto login(String mobile);

    /**
     * 退出
     *
     * @return
     */
    void logout();

    /**
     * 注册用户
     *
     * @param memberLoginRequestDto
     * @return
     */
    Long create(MemberLoginRequestDto memberLoginRequestDto);

    /**
     * 发送短信
     *
     * @param phone
     * @param codeType
     * @param smsTemplate
     * @param templateParam
     */
    void sendSmsCode(String phone, String codeType, String smsTemplate, Map<String, String> templateParam);

    /**
     * 发送短信
     *
     * @param phone
     * @param codeType
     * @param codeType
     */
    void sendSmsCodeThirdParty(String phone, String codeType, String content, String smsCode);

    /**
     * 批量发送短信
     *
     * @param ids
     * @param content
     */
    void sendSmsCodeThirdPartyBatch(List<Long> ids, String content);

    /**
     * 获取验证码
     *
     * @return
     */
    GraphVerifyCodeDto getGraphVerifyCode();

    /**
     * 忘记密码
     *
     * @return
     */
    void fgPwd(MemberLoginRequestDto memberLoginRequestDto);

    /**
     * 更新用户姓名
     *
     * @param id
     * @param userName
     */
    void updateUser(Long id, String userName);

    /**
     * 查询用户信息
     *
     * @param currentUserId
     */
    Users queryUser(Long currentUserId);

    /**
     * 检查升级用户等级
     *
     * @param leaderId
     */
    void checkUpgrade(Long leaderId);

    /**
     * 批量冻结操作
     *
     * @param userIds
     * @param type
     */
    void lock(List<Long> userIds, Integer type);

    /**
     * 获取下载地址
     *
     * @param sysType 1=苹果；2=安卓
     * @return
     */
    String downUrl(Integer sysType);

    /**
     * 首页数据
     *
     * @return
     */
    HomeDto home();

    /**
     * 新增用户折线图
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<newUsersDto> newUsers(String startDate, String endDate);

    /**
     * 查询team对应等级数量
     *
     * @param id
     * @return
     */
    UserTeamDto getTeamLevelNum(Long id);
}