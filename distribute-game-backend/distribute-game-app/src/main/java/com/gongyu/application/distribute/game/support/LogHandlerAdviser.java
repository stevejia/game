package com.gongyu.application.distribute.game.support;


import com.alibaba.fastjson.JSON;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogHandlerAdviser {

    private Logger logger = LoggerFactory.getLogger(LogHandlerAdviser.class);

    public static ThreadLocal<Method> currentMethod = new ThreadLocal<>();
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("execution(* com.gongyu.application.distribute.game.controller.*.*(..)) " +
            "&& !execution(* com.gongyu.application.distribute.game.controller.AliyunFileController.*(..))" +
            "&& !execution(* com.gongyu.application.distribute.game.controller.UsersController.*(..))")
    public void controllerPointCut() {
    }

/**
     * 方法优先级，建议在所有切面之前
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */


    @Order(1000)
    @Around("controllerPointCut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        Method method = null;
        try {
            method = reflectMethod(joinPoint.getTarget().getClass(), methodName, args);
            currentMethod.set(method);
        } catch (NoSuchMethodException e) {
            //理论上不存在
            logger.error("反射获取方法失败", e);
            return BaseResponse.error();
        }

        String[] parameterNames = discoverer.getParameterNames(method);

        logger.info("controller: {}, method: {} open", joinPoint.getTarget().getClass().getName(), method.getName());
        for (int i = 0; i < parameterNames.length; i++) {
            //打印所有请求参数
            if (!StringUtils.equals(methodName, "upload")){
                logger.info("请求参数 [{}]: {}", parameterNames[i], JSON.toJSONString(args[i]));
            }
        }

        Object resp = null;
        try {
            resp = joinPoint.proceed(args);
        } finally {
            // 区分日志级别
            boolean isSuccess = false;

            if (resp instanceof BaseResponse) {
                String status = ((BaseResponse) resp).getStatus();
                isSuccess = "1".equals(status);
            }

//            if (isSuccess) {
//                logger.info("controller: {}, method: {} close", joinPoint.getTarget().getClass().getName(), method.getName());
//                logger.info("响应结果 [{}]: {}", method.getReturnType().getName(), JSON.toJSONString(resp));
//            } else {
//                logger.warn("controller: {}, method: {} close", joinPoint.getTarget().getClass().getName(), method.getName());
//                logger.warn("响应结果 [{}]: {}", method.getReturnType().getName(), JSON.toJSONString(resp));
//            }
        }

        return resp;
    }


    //@TODO 反射使用缓存
    private Method reflectMethod(Class<?> aClass, String methodName, Object[] args) throws NoSuchMethodException {
        switch (args.length) {
            case 0:
                return aClass.getMethod(methodName);
            case 1:
                return aClass.getMethod(methodName, args[0].getClass());
            case 2:
                return aClass.getMethod(methodName, args[0].getClass(), args[1].getClass());
            case 3:
                if (args[0].getClass().getName().endsWith("StandardMultipartFile")){
                    return aClass.getMethod(methodName, MultipartFile.class, args[1].getClass(), args[2].getClass());
                }
                return aClass.getMethod(methodName, args[0].getClass(), args[1].getClass(), args[2].getClass());
            case 4:
                return aClass.getMethod(methodName, args[0].getClass(), args[1].getClass(), args[2].getClass(), args[3].getClass());
            case 5:
                return aClass.getMethod(methodName, args[0].getClass(), args[1].getClass(), args[2].getClass(),
                        args[3].getClass(), args[4].getClass());
            case 6:
                return aClass.getMethod(methodName, args[0].getClass(), args[1].getClass(), args[2].getClass(),
                        args[3].getClass(), args[4].getClass(), args[5].getClass());
            case 7:
                return aClass.getMethod(methodName, args[0].getClass(), args[1].getClass(), args[2].getClass(),
                        args[3].getClass(), args[4].getClass(), args[5].getClass(), args[6].getClass());
            default:
                throw new UnsupportedOperationException("too much parameter.");
        }
    }
}
