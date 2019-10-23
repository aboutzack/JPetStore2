package org.csu.jpetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.jpetstore.domain.Account;
import org.csu.jpetstore.service.AccountService;
import org.csu.jpetstore.service.JwtService;
import org.csu.jpetstore.utils.JwtUtil;
import org.csu.jpetstore.utils.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AccoutController {
    @Autowired
    AccountService accountService;
    @Autowired
    JwtService jwtService;

    @PostMapping("/session")
    public ReturnEntity signIn(@RequestBody Map<String, String> params) {
        JSONObject data = new JSONObject();
        String getterToken = params.get("token");
        if (getterToken != null && !jwtService.isJwtFailure(getterToken)) {
            String token = getterToken;
            if (jwtService.isJwtExpired(getterToken)) {
                //过期但未失效，颁发新的token
                token = jwtService.generateNewJwtByOld(getterToken);
            }
            data.put("token", token);
            data.put("username", JwtUtil.decode(token, JwtUtil.SECRET).get("name").asString());
            return ReturnEntity.successResult(data);
        } else {
            Account databaseAccount = accountService.getAccount(params.get("username"), params.get("password"));
            if (databaseAccount != null) {
                String token = jwtService.generateJwtByUsername(databaseAccount.getUsername());
                data.put("username", databaseAccount.getUsername());
                data.put("token", token);
                return ReturnEntity.successResult(data);
            } else {
                return ReturnEntity.failedResult("用户名或密码错误");
            }
        }
    }

    @DeleteMapping("/session")
    public ReturnEntity signOut(@RequestBody Map<String, String> params) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", null);
        return ReturnEntity.successResult(jsonObject);
    }

    @PostMapping("/user")
    public ReturnEntity signUp(@RequestBody Account account) {
        JSONObject data = new JSONObject();
        String username = account.getUsername();
        Account databaseAccount = accountService.getAccount(username);
        if (databaseAccount != null) {
            return ReturnEntity.failedResult("用户已存在");
        } else {
            accountService.insertAccount(account);
            String token = jwtService.generateJwtByUsername(username);
            data.put("username", username);
            data.put("token", token);
            return ReturnEntity.successResult(data);
        }
    }

    @PutMapping("/user/detailinfo")
    public ReturnEntity updateUserInfo(@RequestBody Account account, @CookieValue("token") String token) {
        JSONObject data = new JSONObject();
        if (!jwtService.isJwtValid(token) || jwtService.isJwtFailure(token)) {
            return ReturnEntity.failedResult("token无效");
        } else if (jwtService.isJwtExpired(token)) {
            String newToken = jwtService.generateNewJwtByOld(token);
            data.put("token", newToken);
        } else {
            data.put("token", token);
        }
        accountService.updateAccount(account);
        data.put("account", account);
        return ReturnEntity.successResult(data);
    }

    @RequestMapping("/user/detailinfo")
    public ReturnEntity getUserInfo(@CookieValue("token") String token) {
        JSONObject data = new JSONObject();
        if (!jwtService.isJwtValid(token) || jwtService.isJwtFailure(token)) {
            return ReturnEntity.failedResult("token无效");
        } else if (jwtService.isJwtExpired(token)) {
            String newToken = jwtService.generateNewJwtByOld(token);
            data.put("token", newToken);
        } else {
            data.put("token", token);
        }
        String userName = JwtUtil.decode(token, JwtUtil.SECRET).get("name").asString();
        Account account = accountService.getAccount(userName);
        data.put("account", account);
        return ReturnEntity.successResult(data);
    }
}
