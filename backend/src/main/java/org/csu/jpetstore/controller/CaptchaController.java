package org.csu.jpetstore.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.csu.jpetstore.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@RestController
public class CaptchaController {
    @Autowired
    DefaultKaptcha producer;
    @Autowired
    CaptchaService captchaService;
    @GetMapping("/captcha")
    public Map<String, Object> captcha(HttpServletResponse response) throws ServletException, IOException {

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = producer.createImage(text);
        outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        // 对字节数组Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        // 生成captcha的token
        Map<String, Object> map = captchaService.createToken(text);
        map.put("img", encoder.encodeToString(outputStream.toByteArray()));
        return map;
    }
}
