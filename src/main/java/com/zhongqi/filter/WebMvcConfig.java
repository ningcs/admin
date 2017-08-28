package com.zhongqi.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zhaocheng
 * @since 2017-2-20
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	
	
//	@Autowired
//	private SecurityInterceptor securityInterceptor;
	@Bean
	public SecurityInterceptor cecurityInterceptor(){
		return new SecurityInterceptor();
	}


	/**
	 * session拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		.excludePathPatterns("/login/**").addPathPatterns("/user/**")
		registry.addInterceptor(cecurityInterceptor()).addPathPatterns("admin/**").excludePathPatterns("login/")
//		registry.addInterceptor(securityInterceptor)
		.addPathPatterns("/report/query","/sign/queryInfo","/user/queryUser","/holiday/setUp","/holiday/query",
		"/report/export","/common/createQrcode","/wxConfig/findBySpId","/sign/setUp","/wxConfig/add","/wxConfig/update");
	}
	
}
