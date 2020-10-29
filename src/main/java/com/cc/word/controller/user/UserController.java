package com.cc.word.controller.user;

import com.cc.word.config.TimeAspectConfig;
import com.cc.word.model.common.ReturnCode;
import com.cc.word.model.common.ReturnResult;
import com.cc.word.model.user.User;
import com.cc.word.service.user.UserService;
import com.cc.word.utils.JWTUtils;
import com.cc.word.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pri/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 新增用户
     */
    @RequestMapping("register")
    public JsonData register(@RequestBody Map<String,String> userInfo){

        JsonData result = userService.createNewUser(userInfo);
        return result;
    }

    @RequestMapping("login")
    public JsonData login(@RequestBody Map<String,String> userInfo){

        JsonData result = userService.selectUser(userInfo);
        if(result.getCode()!=0){
            return result;
        }
        List userList=(List)result.getData();
        if(userList.size()!=1){
            logger.info("login错误,用户名:"+userInfo.get("username")+"");
            return JsonData.buildFail("登陆错误，请联系管理员");
        }
        return JsonData.buildSuccess(JWTUtils.geneJsonWebToken((User)userList.get(0)));
    }

    @RequestMapping("getInfo")
    public JsonData getInfo(HttpServletRequest request){
        Integer userId=(Integer) request.getAttribute("user_id");
        if(userId == null){
            return JsonData.buildFail("查询失败");
        }
        JsonData result = userService.selectUserInfoById(userId);
        return result;
    }

}
