package com.gongyu.application.distribute.game;

import com.gongyu.application.distribute.game.interceptors.LoginInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootApplication(scanBasePackages = {
        "com.gongyu.snowcloud.framework",
        "com.gongyu.service.distribute.game",
        "com.gongyu.application.distribute.game"
        })
@MapperScan(basePackages = {"com.gongyu.*.**.mapper"})
@EnableTransactionManagement
@EnableSwagger2
@EnableAsync
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
public class DistributeGameAppApplication extends SpringBootServletInitializer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(DistributeGameAppApplication.class, args);
    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        PermissionFilter permissionFilter = new PermissionFilter();
//        registrationBean.setFilter(permissionFilter);
//        List<String> urlPatterns = new ArrayList<>();
//        urlPatterns.add("/*");
//        registrationBean.setUrlPatterns(urlPatterns);
//        return registrationBean;
//    }

    @Value("#{'${game.noLoginUrl:}'.split(',')}")
    private Set<String> noLoginUrl;
    @Value("${game.rootUrl:}")
    private String rootUrl;
    @Value("#{'${game.signUrl.notCheck:}'.split(',')}")
    private List<String> notCheckSignUrl;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        List<String> noLoginUrlList = new ArrayList<>();
        for (String url : noLoginUrl) {
            noLoginUrlList.add(url.trim());
        }
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(loginInterceptor)
                        .addPathPatterns(rootUrl)
                        .excludePathPatterns(noLoginUrlList);
            }
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DistributeGameAppApplication.class);
    }
}
