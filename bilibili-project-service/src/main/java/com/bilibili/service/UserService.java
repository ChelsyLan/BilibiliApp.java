package com.bilibili.service;

import com.bilibili.dao.UserDao;
import com.bilibili.domain.User;
import com.bilibili.domain.UserInfo;
import com.bilibili.domain.constant.UserConstant;
import com.bilibili.domain.exception.ConditionException;
import com.bilibili.service.util.MD5Util;
import com.bilibili.service.util.RSAUtil;
import com.bilibili.service.util.TokenUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    //register by Phone number
    public void addUser(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("the Phone cannot be null");
        }
        //whether it has been registered(Service-UserDao-XML-if select is not null)
        User dbUser = this.getUserbyPhone(phone);
        if (dbUser != null) {
            throw new ConditionException("the Phone has been registered");
        }
        //add user
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String rawPassword = user.getPassword();
        try {
            String password = RSAUtil.decrypt(rawPassword);
        } catch (Exception e) {
            throw new ConditionException("encryption failed");
        }
        ;
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");

        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
        userDao.addUser(user);

        //add user information
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(user.getId());
        userInfo.setNickname(UserConstant.DEFAULT_NICKNAME);//设置一个UserConstant储存预设信息
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_FEMALE);
        userInfo.setCreateTime(now);
        userDao.addUserInfo(userInfo);

    }

    public User getUserbyPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    public String login(User user) throws Exception {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("the Phone cannot be null");
        }
        User dbUser = this.getUserbyPhone(phone);
        if (dbUser == null) {
            throw new ConditionException("the user does not exist!");
        }
        //change the password input by user to md5Password
        String rawPassword = user.getPassword();
        String password;
        try {
            password = RSAUtil.decrypt(rawPassword);
        } catch (Exception e) {
            throw new ConditionException("encryption failed");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(password, salt, "UTF-8");
        //compare
        if (!md5Password.equals(dbUser.getPassword())) {
            throw new ConditionException("The password is not correct.");
        }
        //generate a token
        TokenUtil tokenUtil = new TokenUtil();
        return TokenUtil.generateToken(dbUser.getId());
    }

    public User getUserInfo(Long id){
        User user=userDao.getUserByID(id);
        UserInfo userInfo=userDao.getUserInfoByUserID(id);
        user.setUserInfo(userInfo);//integrate data in User and UserInfo
        return user;


    }
}
