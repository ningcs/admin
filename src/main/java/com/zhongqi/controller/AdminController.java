package com.zhongqi.controller;

import com.zhongqi.entity.User;
import com.zhongqi.service.UserService;
import com.zhongqi.util.ResponseResult;
import com.zhongqi.util.SHA256;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by ningcs on 2017/8/25.
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserService userService;
    //用户登录
    @RequestMapping("/login")
    public ResponseResult login(String name , String password, String captcha, HttpServletRequest request,
                                HttpServletResponse response)throws Exception {
        HttpSession session = request.getSession();
//        String captchaToken = (String) session.getAttribute("captchaToken");
        String captchaToken ="abcd";
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
        String password1 =SHA256.encrypt(password);
        User user =userService.login(name, SHA256.encrypt(password));
        if (user!=null){
            session.setAttribute("user",user);
            return ResponseResult.successResult("登陆成功");
        }

        return ResponseResult.errorResult("用户名密码错误");
    }






}
