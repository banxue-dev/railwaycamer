package com.admin.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
class WebConfigurer extends WebMvcConfigurerAdapter {
	@Autowired
	AdminConfig adminConfig;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/files/**").addResourceLocations("file:///"+adminConfig.getUploadPath());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry){
		System.out.println("我是MyWebConfig跨域");
		//设置允许跨域的路径
		registry.addMapping("/**")
				//设置允许跨域请求的域名
				.allowedOrigins("*")
				//是否允许证书 不再默认开启
				.allowCredentials(true)
				//设置允许的方法
				.allowedMethods("*")
				//跨域允许时间
				.maxAge(3600);
	}
}