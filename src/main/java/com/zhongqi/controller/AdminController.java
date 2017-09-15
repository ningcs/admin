package com.zhongqi.controller;

import com.zhongqi.dto.WebSiteInfo;
import com.zhongqi.entity.common.User;
import com.zhongqi.service.WebSiteService;
import com.zhongqi.service.common.UserService;
import com.zhongqi.util.ResponseResult;
import com.zhongqi.util.SHA256;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    private WebSiteService webSiteService;

    //用户登录
    @ApiOperation(value = "1",notes = "用户登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseResult login(String name, String password, String captcha, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
//        String captchaToken = (String) session.getAttribute("captchaToken");
        String captchaToken = "abcd";
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
        } else {
            return ResponseResult.errorResult("验证码无效，请从新刷新验证码");
        }
        if (name == null && "".equals(name.trim())) {
            return ResponseResult.errorResult("用户名不能为空");
        }
        if (password == null && "".equals(password.trim())) {
            return ResponseResult.errorResult("密码不能为空");
        }
        String password1 = SHA256.encrypt(password);
        User user = userService.login(name, SHA256.encrypt(password));
        if (user != null) {
            session.setAttribute("user", user);
            return ResponseResult.successResult("登陆成功");
        }

        return ResponseResult.errorResult("用户名密码错误");
    }


    //2.添加网站
    @ApiOperation(value = "2",notes = "添加网站")
    @RequestMapping(value = "/addwebSite",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "webTitle", paramType = "query",value = "网站标题", required = true, dataType = "String",defaultValue = "新闻"),
            @ApiImplicitParam(name = "webLogo", paramType = "query",value = "网站logo", required = true, dataType = "String",defaultValue = "10bdc189151784c1041338b1466c8709"),
            @ApiImplicitParam(name = "domainName", paramType = "query",value = "域名", required = true, dataType = "String",defaultValue = "ningcs.com"),
            @ApiImplicitParam(name = "Keyword", paramType = "query",value = "关键字", required = true, dataType = "String",defaultValue = "网站"),
            @ApiImplicitParam(name = "webDescription", paramType = "query",value = "网站描述", required = true, dataType = "String",defaultValue = "建设网站"),
            @ApiImplicitParam(name = "contactPeople", paramType = "query",value = "联系人", required = true, dataType = "String",defaultValue = "宁春笋"),
            @ApiImplicitParam(name = "CompanyPhone", paramType = "query",value = "公司座机", required = true, dataType = "String",defaultValue = "18310456275"),
            @ApiImplicitParam(name = "mobile", paramType = "query",value = "手机", required = true, dataType = "String",defaultValue = "18310456275"),
            @ApiImplicitParam(name = "email", paramType = "query",value = "Email", required = true, dataType = "String",defaultValue = "ningchunsun@163.com"),
            @ApiImplicitParam(name = "qq", paramType = "query",value = "QQ", required = true, dataType = "String",defaultValue = "923507613"),
            @ApiImplicitParam(name = "faxes", paramType = "query",value = "传真", required = true, dataType = "String",defaultValue = "2323"),
            @ApiImplicitParam(name = "address", paramType = "query",value = "地址", required = true, dataType = "String",defaultValue = "东城区"),
            @ApiImplicitParam(name = "webstat", paramType = "query",value = "底部信息", required = true, dataType = "String",defaultValue = "京公网安备11000002000001号"),
    })
    public ResponseResult addwebSite(String webTitle, //网站
                                     String webLogo, //网站logo
                                     String domainName, //域名
                                     String Keyword,//关键字
                                     String webDescription,//网站描述
                                     String contactPeople,//联系人
                                     String CompanyPhone,//公司座机
                                     String mobile,//手机
                                     String email,//Email
                                     String qq,//QQ
                                     String faxes,//传真
                                     String address,//地址
                                     String webstat//底部信息
    ) {

        WebSiteInfo webSiteInfo =null;
        webSiteInfo=this.addWebSiteInfo(webTitle,webLogo, domainName, Keyword,
                webDescription, contactPeople, CompanyPhone,
                mobile, email, qq, faxes, address, webstat);
        if (webSiteInfo!=null){
            webSiteService.addwebSite(webSiteInfo);
            return ResponseResult.successResult("添加网站成功");
        }
        return ResponseResult.errorResult("添加失败，请稍后重新添加");

    }





    private WebSiteInfo addWebSiteInfo(String webTitle, //网站
                                       String webLogo, //网站logo
                                       String domainName, //域名
                                       String Keyword,//关键字
                                       String webDescription,//网站描述
                                       String contactPeople,//联系人
                                       String CompanyPhone,//公司座机
                                       String mobile,//手机
                                       String email,//Email
                                       String qq,//QQ
                                       String faxes,//传真
                                       String address,//地址
                                       String webstat//底部信息
    ) {

        WebSiteInfo webSiteInfo = new WebSiteInfo();
        webSiteInfo.setWebTitle(webTitle);
        webSiteInfo.setEmail(email);
        webSiteInfo.setAddress(address);
        webSiteInfo.setCompanyPhone(CompanyPhone);
        webSiteInfo.setContactPeople(contactPeople);
        webSiteInfo.setDomainName(domainName);
        webSiteInfo.setFaxes(faxes);
        webSiteInfo.setKeyword(Keyword);
        webSiteInfo.setMobile(mobile);
        webSiteInfo.setQq(qq);
        webSiteInfo.setWebDescription(webDescription);
        webSiteInfo.setWebLogo(webLogo);
        webSiteInfo.setWebstat(webstat);
        return webSiteInfo;
    }
}