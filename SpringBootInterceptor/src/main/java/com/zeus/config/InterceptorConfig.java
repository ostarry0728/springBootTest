package com.zeus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zeus.common.Interceptor.AccessLoggingInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private AccessLoggingInterceptor accessLoggingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessLoggingInterceptor).addPathPatterns("/**")
				.excludePathPatterns("/resources/**");

		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
