//package com.zhongqi.controller;
//
//import com.zhongqi.dto.CaptchaInfo;
//import com.zhongqi.service.CaptchaService;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * Created by ningcs on 2017/8/28.
// */
//@Controller
//@RequestMapping("common")
//public class CommonController {
//    private final Log logger = LogFactory.getLog(getClass());
//    @Autowired
//    private CaptchaService captchaService;
//
//    //生成验证码 ok 0522
//    @RequestMapping("/createCaptcha")
//    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        CaptchaInfo captchaInfo = captchaService.createCaptcha();
//        String token = captchaInfo.getToken();
//        String base64String = captchaInfo.getBase64String();
//
//        token = captchaInfo.getToken();
//        HttpSession session = request.getSession(true);
//        session.setAttribute("captchaToken", token);
//
//        byte[] fileBytes = Base64.decodeBase64(base64String);
//        response.getOutputStream().write(fileBytes);
//
//        setResponseHeaders(response);
//    }
//
//
//
//
//    protected void setResponseHeaders(HttpServletResponse response) {
//        response.setContentType("image/png");
//        response.setHeader("Cache-Control", "no-cache, no-store");
//        response.setHeader("Pragma", "no-cache");
//        long time = System.currentTimeMillis();
//        response.setDateHeader("Last-Modified", time);
//        response.setDateHeader("Date", time);
//        response.setDateHeader("Expires", time);
//    }
//
//}
