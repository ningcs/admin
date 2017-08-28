//package com.zhongqi.filter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Created by ningcs on 2017/8/25.
// */
//public class LoginInterceptor extends HandlerInterceptorAdapter {
//    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String authorization = request.getHeader("Authorization");
//        logger.info("The authorization is: {}",authorization);
//        return super.preHandle(request, response, handler);
//    }
//}
