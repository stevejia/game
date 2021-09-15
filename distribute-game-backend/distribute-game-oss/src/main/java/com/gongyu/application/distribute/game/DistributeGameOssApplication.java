package com.gongyu.application.distribute.game;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = { "com.gongyu.snowcloud.framework", "com.gongyu.service.distribute.game",
		"com.gongyu.application.distribute.game",
		"com.gongyu.bizworks.*" }, exclude = DruidDataSourceAutoConfigure.class)
@MapperScan(basePackages = { "com.gongyu.*.**.mapper" })
@EnableTransactionManagement
@EnableSwagger2
@EnableAsync
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
public class DistributeGameOssApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DistributeGameOssApplication.class, args);
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

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DistributeGameOssApplication.class);
	}
}
