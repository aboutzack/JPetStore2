package org.csu.jpetstore.service;

import org.csu.jpetstore.domain.Captcha;
import org.csu.jpetstore.persistence.CaptchaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CaptchaService {
    @Autowired
    private CaptchaMapper captchaMapper;
    //1小时后过期
    private final static int EXPIRE = 3600 * 1;

    public Captcha queryByCaptcha(String captcha) {
        return captchaMapper.queryByCaptcha(captcha);
    }

    public Captcha queryByToken(String token) {
        return captchaMapper.queryByToken(token);
    }

    public void save(Captcha token){
        captchaMapper.save(token);
    }

    public void update(Captcha token){
        captchaMapper.update(token);
    }

    public boolean isExpired(Date expireTime){
        Date d=new Date();
        return d.getTime()>expireTime.getTime()?true:false;
    }

    public Map<String, Object> createToken(String captcha) {
        //生成一个token
        String token = UUID.randomUUID().toString();
        //当前时间
        Date now = new Date();

        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        Captcha tokenEntity = queryByCaptcha(captcha);
        if(tokenEntity == null){
            tokenEntity = new Captcha();
            tokenEntity.setCaptcha(captcha);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            save(tokenEntity);
        }else{
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            update(tokenEntity);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", EXPIRE);

        return map;
    }

    public void deleteByToken(String token) {
        captchaMapper.deleteByToken(token);
    }
}
