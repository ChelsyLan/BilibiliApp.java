package com.bilibili.dao;

import com.bilibili.domain.User;
import com.bilibili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

//User Registration
@Mapper//可以和Mybatis产生关联，自动映射到xml文件里
public interface UserDao {
    User getUserByPhone(String phone);

    Integer addUser(User user);

    Integer addUserInfo(UserInfo userInfo);

    User getUserByID(Long id);

    UserInfo getUserInfoByUserID(Long id);


}
