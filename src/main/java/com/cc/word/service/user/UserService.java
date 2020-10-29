package com.cc.word.service.user;

import com.cc.word.model.common.ReturnResult;
import com.cc.word.model.user.User;
import com.cc.word.utils.JsonData;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 新增用户
     */
    JsonData createNewUser(Map<String,String> userinfo);

    /**
     * 查询用户(登陆使用)
     * param:username,password
     */
    JsonData selectUser(Map<String,String> userinfo);

    /**
     * 查询用户(用户信息)
     * param:username
     */
    JsonData selectUserInfoById(Integer userId);

}
