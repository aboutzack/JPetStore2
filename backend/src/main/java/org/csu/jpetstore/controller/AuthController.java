package org.csu.jpetstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.csu.jpetstore.domain.Account;
import org.csu.jpetstore.domain.Github;
import org.csu.jpetstore.domain.GithubUser;
import org.csu.jpetstore.service.AccountService;
import org.csu.jpetstore.service.GithubService;
import org.csu.jpetstore.service.JwtService;
import org.csu.jpetstore.utils.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
@RequestMapping
public class AuthController {

    @Autowired
    GithubService githubService;
    @Autowired
    JwtService jwtService;
    @Autowired
    AccountService accountService;

    @PostMapping("/auth/access_token/{source}")
    public ReturnEntity getAccessToken(@PathVariable("source") String source, @RequestBody AuthCallback authCallback) {
        //由于向第三方请求code的请求是前端发出的,这里将将state设置为任意相同值,取消state的检查
        AuthRequest authRequest = getAuthRequest(source);
        Random random = new Random();
        random.nextInt();
        authCallback.setState("123");
        //注册state,绕过state的检查
        authRequest.authorize("123");
        AuthResponse authResponse = authRequest.login(authCallback);
        if (!authResponse.ok()) {
            return ReturnEntity.failedResult(authResponse.getMsg());
        }
        //github认证成功
        JSONObject result = new JSONObject();
        if (source.equalsIgnoreCase("github")) {
            JSONObject data = (JSONObject)JSON.toJSON(authResponse.getData());
            String githubId = data.getString("username");
            Github github = githubService.getGithub(githubId);
            if (github != null) {//当前github账号登入过
                String userId = github.getUserId();
                if (userId != null && !userId.isEmpty()) {//已绑定userId
                    Account account = accountService.getAccount(github.getUserId());
                    if (github.getStatus().equals("p") && account != null) {//已完成第三方登入的所有流程
                        String token = jwtService.generateJwtByUsername(account.getUsername());
                        result.put("action", "complete");
                        result.put("token", token);
                        result.put("account", account);
                        return ReturnEntity.successResult(result);
                    }
                } else {//未绑定userId
                    result.put("action", "register");
                    result.put("github_data", github);
                    return ReturnEntity.successResult(result);
                }
            } else {//当前github账号未登入过
                String githubName = data.getString("nickname");
                String avatar = data.getString("avatar");
                String email = data.getString("email");
                github = new Github(null, githubId, githubName, avatar, email, "w");
                githubService.insert(github);
                result.put("action", "register");
                result.put("github_data", github);
                return ReturnEntity.successResult(result);
            }
        }
        return ReturnEntity.successResult(authResponse.getData());
    }

    @PostMapping("/github_user")
    public ReturnEntity signUpGithubUser(@RequestBody GithubUser githubUser) {
        String githubId = githubUser.getGithubId();
        Account account = githubUser.getAccount();
        if (account == null)
            return ReturnEntity.failedResult("account == null");
        if (githubId == null)
            return ReturnEntity.failedResult("githubId == null");
        String username = account.getUsername();
        if (username == null)
            return ReturnEntity.failedResult("username不能为空");
        if (account.getPassword() == null)
            return ReturnEntity.failedResult("password不能为空");
        if (account.getAddr1() == null)
            return ReturnEntity.failedResult("addr1不能为空");
        if (account.getEmail() == null)
            return ReturnEntity.failedResult("email不能为空");
        if (account.getFirstName() == null)
            return ReturnEntity.failedResult("first name不能为空");
        if (account.getLastName() == null)
            return ReturnEntity.failedResult("last name不能为空");
        if (account.getCity() == null)
            return ReturnEntity.failedResult("city不能为空");
        if (account.getState() == null)
            return ReturnEntity.failedResult("state不能为空");
        if (account.getZip() == null)
            return ReturnEntity.failedResult("zip不能为空");
        if (account.getCountry() == null)
            return ReturnEntity.failedResult("country不能为空");
        if (account.getPhone() == null)
            return ReturnEntity.failedResult("phone不能为空");
        if (account.getLanguagePreference() == null)
            return ReturnEntity.failedResult("language preference不能为空");

        Github github = githubService.getGithub(githubId);
        if (github == null)
            return ReturnEntity.failedResult("github账号不存在");
        if (github.getStatus().equals("p"))
            return ReturnEntity.failedResult("该github账户已绑定");
        Account databaseAccount = accountService.getAccount(username);
        if(databaseAccount != null)
            return ReturnEntity.failedResult("用户id已存在");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountService.insertAccount(account);
        github.setStatus("p");
        github.setUserId(username);
        githubService.update(github);
        String token = jwtService.generateJwtByUsername(username);
        JSONObject data = new JSONObject();
        data.put("token", token);
        data.put("username", username);
        return ReturnEntity.successResult(data);
    }

    private AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source) {
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId(org.csu.jpetstore.config.AuthConfig.GITHUB_CLIENT_ID)
                        .clientSecret(org.csu.jpetstore.config.AuthConfig.GITHUB_CLIENT_SECRET)
                        .redirectUri(org.csu.jpetstore.config.AuthConfig.GITHUB_REDIRECT_URL)
                        .build());
                break;
        }
        if (null == authRequest) {
            throw new AuthException("未获取到有效的Auth配置");
        }
        return authRequest;
    }
}