//package com.zhongqi.filter;
//
//import com.zhongqi.AdminApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
///**
// * Created by ningcs on 2017/8/25.
// */
//@Configuration
//@ComponentScan(basePackageClasses = AdminApplication.class, useDefaultFilters = true)
//public class ServletContextConfig extends WebMvcConfigurationSupport {
//
//    @Bean
//    public LoginInterceptor loginInterceptor() {
//        return new LoginInterceptor();
//    }
//
//    /**
//     * 配置servlet处理
//     */
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login/**")
//        ;
//    }
//}
