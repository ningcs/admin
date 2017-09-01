package com.zhongqi.controller;

import com.zhongqi.dto.CaptchaInfo;
import com.zhongqi.dto.FileInfo;
import com.zhongqi.service.CaptchaService;
import com.zhongqi.service.FileService;
import com.zhongqi.util.ResponseResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
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

    //生成验证码 ok 0522
    @RequestMapping("/createCaptcha")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaInfo captchaInfo = captchaService.createCaptcha();
        String token = captchaInfo.getToken();
        String base64String = captchaInfo.getBase64String();

        token = captchaInfo.getToken();
        HttpSession session = request.getSession(true);
        session.setAttribute("captchaToken", token);

        byte[] fileBytes = Base64.decodeBase64(base64String);
        response.getOutputStream().write(fileBytes);

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



    protected void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
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
