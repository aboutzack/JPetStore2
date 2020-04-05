package org.csu.jpetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.jpetstore.annotation.Authorization;
import org.csu.jpetstore.domain.Account;
import org.csu.jpetstore.domain.Captcha;
import org.csu.jpetstore.service.AccountService;
import org.csu.jpetstore.service.CaptchaService;
import org.csu.jpetstore.service.JwtService;
import org.csu.jpetstore.utils.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    JwtService jwtService;
    @Autowired
    CaptchaService captchaService;

    @PostMapping("/session")
    public ReturnEntity signIn(@RequestBody Map<String, String> params) {
        JSONObject data = new JSONObject();
        String password = params.get("password");
        String username = params.get("username");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = accountService.getPassword(username);
        System.out.println(encodedPassword);
        if (encodedPassword == null || encodedPassword.isEmpty()) {
            return ReturnEntity.failedResult("用户名不存在");
        }
        if (!passwordEncoder.matches(password, encodedPassword)) {
            return ReturnEntity.failedResult("用户名或密码错误");
        }
        Account account = accountService.getAccount(username);
        System.out.println(account);
        if (account == null) {
            return ReturnEntity.failedResult("用户名不存在");
        }
        String token = jwtService.generateJwtByUsername(account.getUsername());
        data.put("account", account);
        data.put("token", token);
        return ReturnEntity.successResult(data);
    }

    @DeleteMapping("/session")
    @Authorization
    public ReturnEntity signOut(@RequestBody Map<String, String> params) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", null);
        return ReturnEntity.successResult(jsonObject);
    }

    @PostMapping("/user")
    public ReturnEntity signUp(@RequestBody Account account) {
        JSONObject data = new JSONObject();
        String username = account.getUsername();
        String password = account.getPassword();
        Account databaseAccount = accountService.getAccount(username);
        if (databaseAccount != null) {
            return ReturnEntity.failedResult("用户已存在");
        } else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //加密password
            account.setPassword(passwordEncoder.encode(password));
            accountService.insertAccount(account);
            String token = jwtService.generateJwtByUsername(username);
            data.put("username", username);
            data.put("token", token);
            return ReturnEntity.successResult(data);
        }
    }

    /**
     * 注册前判断用户是否存在
     *
     * @param params
     * @return
     */
    @GetMapping("/user")
    public ReturnEntity getUser(@RequestParam Map<String, String> params) {
        String username = params.get("username");
        String captcha = params.get("captcha");
        Account account = accountService.getAccount(username);

        if (account != null) {
            return ReturnEntity.failedResult("用户已存在");
        } else {
            return ReturnEntity.successResult(username);
        }
    }

    @PutMapping("/user/detailinfo")
    @Authorization
    public ReturnEntity updateUserInfo(@RequestBody Account account, @RequestAttribute String username) {
        if (account == null) {
            return ReturnEntity.failedResult("缺少参数account");
        }
        JSONObject data = new JSONObject();
        accountService.updateAccount(account);
        data.put("account", account);
        return ReturnEntity.successResult(data);
    }

    @GetMapping("/user/detailinfo")
    @Authorization
    public ReturnEntity getUserInfo(@RequestAttribute String username) {
        JSONObject data = new JSONObject();
        Account account = accountService.getAccount(username);
        data.put("account", account);
        return ReturnEntity.successResult(data);
    }
}
