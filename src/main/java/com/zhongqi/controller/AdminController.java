package com.zhongqi.controller;

import com.zhongqi.entity.User;
import com.zhongqi.util.ResponseResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by ningcs on 2017/8/25.
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    Log logger = LogFactory.getLog(getClass());
    //用户登录
    @RequestMapping("/login")
    @ResponseBody
    public ResponseResult login(String name , String password, String captcha, HttpServletRequest request,
                                HttpServletResponse response)throws Exception {
        HttpSession session = request.getSession();
        String captchaToken = (String) session.getAttribute("captchaToken");

        logger.info("captcha input:" + captcha);
        logger.info("captcha session:" + captchaToken);
        if (captcha != null && !"".equals(captcha.trim())
                && captchaToken != null && !"".equals(captchaToken.trim())) {

            captcha = captcha.toLowerCase();
            captchaToken = captchaToken.toLowerCase();

            //检查比对图形验证码
            if (!captchaToken.equals(captcha)) {
                return ResponseResult.errorResult("验证码输入错误");
            }
        }else {
            return ResponseResult.errorResult("验证码无效，请从新刷新验证码");
        }
        if (name==null && "".equals(name.trim())){
            return ResponseResult.errorResult("用户名不能为空");
        }if (password==null && "".equals(password.trim())){
            return ResponseResult.errorResult("密码不能为空");
        }
        User user =new User();
        user.setName("ningcs");
        user.setPassword("12345678");
        if (name.equals(user.getName())&& password.equals(user.getPassword())){
            session.setAttribute("user",user);
//            response.sendRedirect("/index");
            return ResponseResult.successResult("登陆成功");
        }

        return ResponseResult.errorResult("用户名密码错误");
    }






}
