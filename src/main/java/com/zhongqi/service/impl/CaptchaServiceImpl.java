package com.zhongqi.service.impl;

import com.zhongqi.dto.CaptchaInfo;
import com.zhongqi.service.CaptchaService;
import com.zhongqi.util.CaptchaUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

//import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Created by songrenfei on 2017/2/20.
 */
@Service("captchaService")
public class CaptchaServiceImpl implements CaptchaService {
    @Override
    public CaptchaInfo createCaptcha() {
        CaptchaInfo captchaInfo = new CaptchaInfo();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        CaptchaUtils captchaUtils = new CaptchaUtils();

        String token = captchaUtils.createCaptcha(byteArrayOutputStream);

        String base64String = Base64.encodeBase64String(byteArrayOutputStream.toByteArray());

        captchaInfo.setToken(token);
        captchaInfo.setBase64String(base64String);

        return captchaInfo;
    }
}
