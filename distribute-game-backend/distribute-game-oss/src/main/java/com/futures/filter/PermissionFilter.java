package com.futures.filter;

import com.gongyu.snowcloud.framework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PermissionFilter implements Filter {

    public static List<String> notCheckUrlList = new ArrayList<>();

    static {
        notCheckUrlList.add("v2/api-docs");
        notCheckUrlList.add("swagger-resources");
        notCheckUrlList.add("aliyunFile/upload");
        notCheckUrlList.add("aliyunFile/batchUpload");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json;charset=UTF-8");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI().substring(request.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }

        Long sysUserId = WebUtils.getCurrentUserId();
        log.info("请求地址>>>>>>>>>>>url>>>>>>>>>>" + url);
        log.info("请求用户>>>>>>>>>>>sysUserId>>>>>>>>>>" + sysUserId);

        //免登录直接返回
        if (isIncludeNotCheckUrl(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        // TODO 登录校验

        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }

    private boolean isIncludeNotCheckUrl(String url) {
        //注意前面不要加"/"
        for (String checkUrl : notCheckUrlList) {
            if (url.contains(checkUrl)||url.endsWith(".html")||url.endsWith(".css")||url.endsWith(".js")||url.endsWith(".gif")||url.endsWith(".eot")||url.endsWith(".svg")||url.endsWith(".ttf")||url.endsWith(".woff")) {
                return true;
            }
        }
        return false;
    }
}
