package com.rain.dao;

import com.rain.pojo.User;

public interface UserMapper {
    User selectOne(int id);
    User selectUser(User user);
    int insertUser(User user);
    
    int updateNick(User user);
    int updateSex(User user);
    int updateSign(User user);
    int updatePwd(User user);
    int updatePhone(User user);
    int updateEmail(User user);
    User correctPhone(User user);
    User correctEmail(User user);
}
