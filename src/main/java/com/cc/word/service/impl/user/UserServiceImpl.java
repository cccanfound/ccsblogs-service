package com.cc.word.service.impl.user;

import com.cc.word.dao.user.UserDao;
import com.cc.word.model.common.ReturnCode;
import com.cc.word.model.common.ReturnResult;
import com.cc.word.model.user.User;
import com.cc.word.service.user.UserService;
import com.cc.word.utils.BaseUtil;
import com.cc.word.utils.CommonUtils;
import com.cc.word.utils.JWTUtils;
import com.cc.word.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    /**
     * 新增用户
     */
    @Override
    public JsonData createNewUser(Map<String, String> userInfo) {
        ReturnResult result = checkCreateNewUser(userInfo);
        if(!"0".equals(result.getReturnCode())){
            return JsonData.buildFail(result.getReturnMsg());
        }
        try {
            //查询该用户名是否已经存在
            Map<String, String> usernameInfo = new HashMap<String, String>();
            usernameInfo.put("username",userInfo.get("username"));
            User user = parseToUser(usernameInfo);
            logger.info("selectUser查询用户名是否以及存在，参数：",user);
            List<User> userList = userDao.selectUser(user);
            logger.info("selectUser查询用户名是否以及存在，结果：",userList);
            if(userList.size()>0){
                return JsonData.buildFail("已经有同名用户了喔");
            }
            user = parseToUser(userInfo);
            logger.info("userDao.save插入用户，参数：",user);
            int rows = userDao.save(user);
            logger.info("userDao.save插入用户，结果：",rows);
            return rows == 1 ? JsonData.buildSuccess(userInfo):JsonData.buildFail("注册失败，请联系管理员");
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("新增用户异常，原因为:[{}]",e.getMessage(),e);
            return JsonData.buildError("运行错误，请联系管理员");
        }

    }

    /**
     * 登陆校验
     */
    public JsonData selectUser(Map<String, String> userInfo) {
        ReturnResult result = checkSelectUser(userInfo);
        if(!"0".equals(result.getReturnCode())){
            return JsonData.buildFail(result.getReturnMsg());
        }
        try {
            User user = parseToUser(userInfo);
            logger.info("selectUser查询用户名是否存在，参数：",user);
            List<User> ListUser = userDao.selectUser(user);
            logger.info("selectUser查询用户名是否存在，结果：",ListUser);
            if(ListUser.size()==0){
                return JsonData.buildFail("用户名或密码错误");
            }
            return JsonData.buildSuccess(ListUser);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("用户登陆异常，原因为:[{}]",e.getMessage(),e);
            return JsonData.buildError("运行错误，请联系管理员");
        }

    }

    @Override
    public JsonData selectUserInfoById(Integer userId) {
        logger.info("selectUserInfo查询用户信息，参数，userid:：",userId);
        User user = userDao.selectUserInfoById(userId);
        if(user==null){
            return JsonData.buildFail("未找到此用户");
        }
        return JsonData.buildSuccess(user);
    }

    private User parseToUser(Map<String, String> userinfo) {
            User user=new User();
            user.setUserName(userinfo.get("username"));
            user.setHeadImg(userinfo.get("head_img"));
            user.setPassword(CommonUtils.MD5(userinfo.get("password")));
            user.setCreateTime(new Date());
            return user;
    }

    private ReturnResult checkCreateNewUser(Map<String,String> userInfo){
        ReturnResult result = new ReturnResult();
        if(userInfo == null || userInfo.size() == 0){
            logger.info("createNewUser[{}]参数userInfo为空",userInfo);
            result.setReturnCode(ReturnCode.param_miss.returnCode);
            result.setReturnMsg("classAdd传入的classInfo为空！");
            return result;
        }
        String userName=userInfo.get("username");
        String password=userInfo.get("password");
        //参数非空校验
        Map<String, String> checkMap = new HashMap();
        checkMap.put("userName", userName);
        checkMap.put("password", password);
        result = BaseUtil.parameterNotBankCheck(checkMap);
        return result;
    }

    private ReturnResult checkSelectUser(Map<String,String> userInfo){
        ReturnResult result = new ReturnResult();
        if(userInfo == null || userInfo.size() == 0){
            logger.info("createNewUser[{}]参数userInfo为空",userInfo);
            result.setReturnCode(ReturnCode.param_miss.returnCode);
            result.setReturnMsg("classAdd传入的classInfo为空！");
            return result;
        }
        String userName=userInfo.get("username");
        String password=userInfo.get("password");
        //参数非空校验
        Map<String, String> checkMap = new HashMap();
        checkMap.put("userName", userName);
        checkMap.put("password", password);
        result = BaseUtil.parameterNotBankCheck(checkMap);
        return result;
    }
}
