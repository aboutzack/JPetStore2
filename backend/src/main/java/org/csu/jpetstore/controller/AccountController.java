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
        String cToken = params.get("cToken");
        String captcha = params.get("captcha");
        if (captcha == null || captcha.isEmpty())
            return ReturnEntity.failedResult("验证码答案不能为空");
        if (cToken == null || cToken.isEmpty())
            return ReturnEntity.failedResult("验证码唯一标识不能为空");
        Captcha captchaEntity = captchaService.queryByToken(cToken);
        if (captchaEntity == null)
            return ReturnEntity.failedResult("验证码唯一标识无效");
        if (!captcha.equalsIgnoreCase(captchaEntity.getCaptcha()))
            return ReturnEntity.failedResult("验证码答案不正确");

        Account databaseAccount = accountService.getAccount(params.get("username"), params.get("password"));
        if (databaseAccount != null) {
            String token = jwtService.generateJwtByUsername(databaseAccount.getUsername());
            data.put("account", databaseAccount);
            data.put("token", token);
            return ReturnEntity.successResult(data);
        } else {
            return ReturnEntity.failedResult("用户名或密码错误");
        }
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

    /**
     * 注册前判断用户是否存在
     *
     * @param params
     * @return
     */
    @GetMapping("/user")
    public ReturnEntity getUser(@RequestParam Map<String, String> params) {
        String username = params.get("username");
        String cToken = params.get("cToken");
        String captcha = params.get("captcha");
        Account account = accountService.getAccount(username);
        Captcha captchaEntity = captchaService.queryByToken(cToken);

        if (captcha == null || captcha.isEmpty()) {
            return ReturnEntity.failedResult("验证码不能为空");
        } else if (cToken == null || cToken.isEmpty()) {
            return ReturnEntity.failedResult("cToken不能为空");
        } else if (!captcha.equalsIgnoreCase(captchaEntity.getCaptcha())) {
            return ReturnEntity.failedResult("验证码不正确");
        } else if (account != null) {
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
