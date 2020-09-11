package com.gongyu.application.distribute.game.interceptors;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gongyu.service.distribute.game.model.entity.Users;
import com.gongyu.service.distribute.game.service.UsersService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Value("#{environment['debug']}")
    private final Boolean debug = false;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UsersService usersService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("请求地址>>>>>>>>>>>" + request.getServletPath());
        log.info("访问路径:{}", request.getServletPath());
        if (debug) {
            log.info("调试模式,登录用户:{}", 2012);
            WebUtils.setCurrentUserId(2110L);
            return true;
        }
        String url = request.getRequestURI().substring(request.getContextPath().length());
        if (url.contains("doc") || url.endsWith(".html") || url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".gif") || url.endsWith(".eot") || url.endsWith(".svg") || url.endsWith(".ttf") || url.endsWith(".woff")) {
            return true;
        }
        String token = request.getHeader("token");
        log.info("请求用户>>>>>>>>>>>token>>>>>>>>>>" + token);
        if (StringUtils.isEmpty(token)) {
            return responseCheck(response, "请登录");
        }
        Users one = usersService.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getToken, token));
        if (one == null) {
            return responseCheck(response, "请登录");
        }
        if (one.getIsLock() == 1) {
            return responseCheck(response, "当前用户被冻结");
        }
        WebUtils.setCurrentUserId(one.getId());
        /*if (WebUtils.getCurrentUserId() != null) {
            Users byId = usersService.getById(WebUtils.getCurrentUserId());
            if (byId == null) {
                return responseCheck(response, "用户不在登录状态");
            }
            if (StringUtils.isEmpty(byId.getToken())) {
                return responseCheck(response, "已经退出账户");
            }
        } else {
            return responseCheck(response, "请登录");
        }*/
        log.info("请求用户>>>>>>>>>>>memberId>>>>>>>>>>" + WebUtils.getCurrentUserId());
        return true;
    }

    private boolean responseCheck(HttpServletResponse response, String message) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(BaseResponse.newInstance("-99", "", message)));
            writer.flush();
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
