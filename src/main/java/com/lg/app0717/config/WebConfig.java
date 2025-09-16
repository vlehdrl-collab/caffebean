package com.lg.app0717.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	// 큰 이미지는 브라우저의 cache 기능을 사용하여 화면 깜빡임 감소
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
        .addResourceHandler("/images/**")
        .addResourceLocations("classpath:/static/images/")
        .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
	}
	
}
