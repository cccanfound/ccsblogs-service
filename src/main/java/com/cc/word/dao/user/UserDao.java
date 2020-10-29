package com.cc.word.dao.user;

import com.cc.word.model.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    /**
     * 新增用户
     */
    int save(User user);
    /**
     * 查询用户
     */
    List<User> selectUser(User user);
    /**
     * 根绝userid查询用户信息
     */
    User selectUserInfoById(Integer userId);
}
