package com.zhongqi.controller;

import com.zhongqi.dto.common.FileInfo;
import com.zhongqi.entity.common.User;
import com.zhongqi.service.common.CaptchaService;
import com.zhongqi.service.common.FileService;
import com.zhongqi.service.common.UserService;
import com.zhongqi.util.ResponseResult;
import com.zhongqi.util.SHA256;
import com.zhongqi.util.ValidateCodeUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ningcs on 2017/8/28.
 */
@Controller
@RequestMapping("common")
public class CommonController {
    private final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    //生成验证码 ok 0522
    @RequestMapping("/createCaptcha")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        HttpSession session = request.getSession();
        ValidateCodeUtil vCode = new ValidateCodeUtil(160,40,5,100);
        session.setAttribute("captchaToken", vCode.getCode());
        vCode.write(response.getOutputStream());
        setResponseHeaders(response);
    }


    //验证验证码 ok 0522
    @ResponseBody
    @RequestMapping("/checkCaptcha")
    public ResponseResult checkCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        Map<String, Object> resultMap = new HashMap<>();

        HttpSession session = request.getSession();
        // TODO: 2017/9/1 验证码为abcd
//        String captchaToken = (String) session.getAttribute("captchaToken");
        String captchaToken ="abcd";
        this.logger.info("captcha input:" + captcha);
        this.logger.info("captcha session:" + captchaToken);

        if (captcha != null && !"".equals(captcha.trim())
                && captchaToken != null && !"".equals(captchaToken.trim())) {

            captcha = captcha.toLowerCase();
            captchaToken = captchaToken.toLowerCase();

            if (captcha.equals(captchaToken)) {
                // 验证码正确!
                resultMap.put("valid", true);
                return new ResponseResult(ResponseResult.SUCCESS,"success",resultMap);
            } else {
                // 验证码错误!
                resultMap.put("valid", false);
                return new ResponseResult(ResponseResult.ERROR,"输入验证码错误",resultMap);
            }
        }
        // 验证码错误!
        resultMap.put("valid", false);
        return new ResponseResult(ResponseResult.ERROR,"输入验证码错误",resultMap);
    }

    //上传文件
    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ResponseResult upload(MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws Exception{
        ResponseResult result = new ResponseResult();
        if (!file.isEmpty()){
            FileInfo fileInfo = new FileInfo();
            BASE64Encoder encoder = new BASE64Encoder();
            String name =file.getOriginalFilename();
            fileInfo.setBase64String(encoder.encode(file.getBytes()));
            fileInfo.setContentType(file.getContentType());
            fileInfo.setFileName(name);
            fileInfo.setSuffix("."+name.substring(name.lastIndexOf(".")+1));
            fileInfo = fileService.saveFile(fileInfo);

            String fileMD5 = fileInfo.getFileMD5();

            if(fileMD5==null || "".equals(fileMD5.trim())){
                result = ResponseResult.errorResult("保存文件失败！");
            }else{
                result = new ResponseResult(ResponseResult.SUCCESS,"保存文件成功！",fileInfo);
            }
        }
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/getFile",method = {RequestMethod.POST})
    public ResponseResult getFile(String fileMD5, HttpServletRequest request, HttpServletResponse response){
        ResponseResult result = new ResponseResult();

        FileInfo fileInfo = fileService.getFile(fileMD5);

        if(fileInfo!=null){
            result = new ResponseResult(ResponseResult.SUCCESS,"获取文件成功！",fileInfo);
        }else{
            result = ResponseResult.errorResult("获取文件失败！");
        }

        return result;
    }
    //获取文件
    @RequestMapping(value = "/getFileInfo",method = {RequestMethod.POST,RequestMethod.GET})
    public FileInfo getFileInfo(String fileMD5, HttpServletRequest request, HttpServletResponse response){
        ResponseResult result = new ResponseResult();

        FileInfo fileInfo = fileService.getFile(fileMD5);

        if(fileInfo!=null){
            result = new ResponseResult(ResponseResult.SUCCESS,"获取文件成功！",fileInfo);
        }else{
            result = ResponseResult.errorResult("获取文件失败！");
        }

        return fileInfo;
    }

    //3.修改密码
    @ApiOperation(value = "3.修改密码",notes = "3.修改密码")
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", paramType = "query",value = "老密码", required = true, dataType = "String",defaultValue = "123456"),
            @ApiImplicitParam(name = "newPassword", paramType = "query",value = "新密码", required = true, dataType = "String",defaultValue = "12345678"),
            @ApiImplicitParam(name = "verifyPassword", paramType = "query",value = "确认密码", required = true, dataType = "String",defaultValue = "12345678"),
    })
    @ResponseBody
    public ResponseResult updatePassword(HttpServletRequest request,
                                         String oldPassword, //老密码
                                         String newPassword //新密码
    ) {
        User user =(User) request.getSession().getAttribute("user");
        if (oldPassword==null && "".equals(oldPassword)){
            return ResponseResult.errorResult("旧密码为空");
        }else if(!user.getPassword().equals(SHA256.encrypt(oldPassword))){
            return ResponseResult.errorResult("旧密码输入错误");
        }
        userService.updatePassword(newPassword,user.getId());
        request.getSession().invalidate();
        return ResponseResult.successResult("密码修改成功");

    }



    protected void setResponseHeaders(HttpServletResponse response) {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }
//    private String getSuffix (String filName){
//        String[] name=filName.split(".");
//        for (){
//
//        }
//
//    }

}
