package org.csu.jpetstore.service;


import com.alibaba.fastjson.JSONObject;
import org.csu.jpetstore.utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String secret = "CaiGuanyuZhangLujunChenJiaqi";
    private final long expireTime = 2*60*60*1000;
    private final long failureTime = 2*24*60*60*1000;

    public String generateJwtByUsername(String username){
        return JwtUtil.encode(username, secret, expireTime);
    }

    /**
     * 由旧的token生成新的token
     *
     * @param token 旧的token
     * @return
     */
    public String generateNewJwtByOld(String token) {
        String name = JwtUtil.decode(token, secret).get("name").asString();
        return JwtUtil.encode(name, secret, expireTime);
    }

    /**
     * 通过验证再加密验证token是否由此server生成
     *
     * @param token
     * @return boolean
     */
    public boolean isJwtValid(String token) {
        return JwtUtil.isJwtValid(token, secret);
    }

    /**
     * 判断token是否超过expireTime
     *
     * @param token
     * @return boolean
     */
    public boolean isJwtExpired(String token) {
        return JwtUtil.isJwtExpired(token, secret);
    }

    /**
     * 判断token是否超过failureTime
     *
     * @param token
     * @return boolean
     */
    public boolean isJwtFailure(String token) {
        return JwtUtil.isJwtFailure(token, secret, failureTime);
    }

    public String validateToken(String token, JSONObject data) {
        if (!isJwtValid(token) || isJwtFailure(token)) {
            return null;
        } else if (isJwtExpired(token)) {
            token = generateNewJwtByOld(token);
            data.put("token", token);
        } else {
            data.put("token", token);
        }
        return token;
    }
}