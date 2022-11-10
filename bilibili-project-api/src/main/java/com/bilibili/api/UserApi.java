package com.bilibili.api;

import com.bilibili.api.support.UserSupport;
import com.bilibili.domain.JsonResponse;
import com.bilibili.domain.User;
import com.bilibili.service.UserService;
import com.bilibili.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@ComponentScan("com.bilibili.service")
public class UserApi {
    @Autowired
    private UserService userService;

    @Autowired
    private UserSupport userSupport;

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        String pk=RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(pk);
    }

    @GetMapping("/users")
    public JsonResponse<User> getUserInfo(){
        Long userID=userSupport.getCurrentUserID();
        User user=userService.getUserInfo(userID);
        return new JsonResponse<>(user);

    }

    @PostMapping("/users")//用户注册
    public JsonResponse<String> addUser(@RequestBody User user){
        return JsonResponse.success();

    }

    @PostMapping("/user-tokens")//User Registration;generate a token
    public JsonResponse<String> login(@RequestBody User user)throws Exception{
        String token=userService.login(user);
        return new JsonResponse<>(token);
    }
}
