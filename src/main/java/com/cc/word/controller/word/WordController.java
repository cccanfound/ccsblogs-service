package com.cc.word.controller.word;

import com.cc.word.controller.user.UserController;
import com.cc.word.model.common.ReturnCode;
import com.cc.word.model.common.ReturnResult;
import com.cc.word.service.user.UserService;
import com.cc.word.service.word.WordService;
import com.cc.word.utils.BaseUtil;
import com.cc.word.utils.JsonData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liyc
 * @date 2020/10/3 22:06
 */
@RestController
@RequestMapping("api/v1/pri/word")
public class WordController {

    private static Logger logger = LoggerFactory.getLogger(WordController.class);

    @Autowired
    private WordService wordService;

    @RequestMapping("list")
    public JsonData list(HttpServletRequest request,@RequestBody Map<String,String> Params) {
        try {
            String type=Params.get("type");
            Integer pageNum = Params.get("page") == null ? 1 : Integer.parseInt(Params.get("page"));
            Integer pageSize = Params.get("limit") == null ? 20 : Integer.parseInt(Params.get("limit"));
            Integer userId = (Integer) request.getAttribute("user_id");
            if (userId == null) {
                return JsonData.buildFail("查询失败");
            }
            Map<String,Object> searchParams =new HashMap<>();
            searchParams.put("type",type);
            searchParams.put("user_id",userId);
            PageHelper.startPage(pageNum, pageSize);
            logger.info("selectUser查询用户名是否以及存在，参数userId,type", searchParams);
            List<Map<String, Object>> list = wordService.selectWordList(searchParams);
            PageInfo pageInfo = new PageInfo(list, pageSize);
            if (list == null) {
                return JsonData.buildFail("Fail,请联系管理员");
            }
            JsonData result = JsonData.buildSuccess(pageInfo);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("查询单词列表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    //更改单词状态
    @RequestMapping("changeState")
    public JsonData changeState(HttpServletRequest request,@RequestBody Map<String,String> Params) {
        try {
            String id=Params.get("id");
            String state=Params.get("state");
            Map<String,Object> changeParams =new HashMap<>();
            changeParams.put("id",id);
            changeParams.put("state",state);
            logger.info("changeState更改单词状态，参数", changeParams);
            int data = wordService.changeWordState(changeParams);
            JsonData result = JsonData.buildSuccess(data);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("查询单词列表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    //向正在学习中添加单词
    @RequestMapping("addWord")
    public JsonData addWord(HttpServletRequest request,@RequestBody Map<String,String> addParams){
        Integer userId = (Integer) request.getAttribute("user_id");
        if (userId == null) {
            return JsonData.buildFail("查询失败");
        }
        addParams.put("userId",userId+"");
        //校验参数
        ReturnResult checkResult = checkAddWord(addParams);
        if(!"0".equals(checkResult.getReturnCode())){
            return JsonData.buildFail(checkResult.getReturnMsg());
        }
        Map<String,Object> params =new HashMap<>();
        params.put("user_id",userId);
        params.put("number",Integer.parseInt(addParams.get("number")));
        try {
            logger.info("addUser添加正在学单词，参数userId,number",params);
            int data = wordService.addWords(params);
            if (data == 0) {
                return JsonData.buildSuccess("恭喜毕业,没有更多了");
            }
            JsonData result = JsonData.buildSuccess(data);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("添加正在需欸单词异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    //查询单词列表
    @RequestMapping("searchWords")
    public JsonData searchWords(HttpServletRequest request,@RequestBody Map<String,String> searchParams){
        Integer pageNum = searchParams.get("page") == null ? 1 : Integer.parseInt(searchParams.get("page"));
        Integer pageSize = searchParams.get("limit") == null ? 20 : Integer.parseInt(searchParams.get("limit"));
        //校验参数
        ReturnResult checkResult = checksearchWords(searchParams);
        if(!"0".equals(checkResult.getReturnCode())){
            return JsonData.buildFail(checkResult.getReturnMsg());
        }
        Map<String,Object> params =new HashMap<>();
        params.put("info",searchParams.get("info"));
        try {
            logger.info("searchWords查询单词列表，参数info",params);
            PageHelper.startPage(pageNum, pageSize);
            List<Map<String, Object>> list = wordService.searchWords(params);
            PageInfo pageInfo = new PageInfo(list, pageSize);
            if (list == null) {
                return JsonData.buildFail("Fail,请联系管理员");
            }
            JsonData result = JsonData.buildSuccess(pageInfo);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("添加正在需欸单词异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    //根据id查询单词
    @RequestMapping("searchWordById")
    public JsonData searchWordById(HttpServletRequest request,@RequestBody Map<String,String> searchParams){
        //校验参数
        if(searchParams == null || searchParams.size() == 0 || searchParams.get("id")==null){
            logger.error("设置单词,根据id查询单词,searchWordById参数为空");
            return JsonData.buildFail("Fail,请联系管理员");
        }
        Map<String,Object> params =new HashMap<>();
        params.put("id",searchParams.get("id"));
        try {
            logger.info("searchWordById查询单个单词信息，参数params",params);
            Map<String, Object> map = wordService.searchWordById(params);
            logger.info("searchWordById查询单词例句，参数params",params);
            List<Map<String,Object>> list =  wordService.searchSentenceById(params);
            map.put("domains",list);
            if (map == null) {
                return JsonData.buildFail("Fail,请联系管理员");
            }
            JsonData result = JsonData.buildSuccess(map);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("添加正在需欸单词异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    //编辑单词
    @RequestMapping("editWord")
    public JsonData editWord(HttpServletRequest request,@RequestBody Map<String,Object> info){
        Integer userId = (Integer) request.getAttribute("user_id");
        String name = request.getAttribute("name").toString();
        if (userId == null || name == null) {
            return JsonData.buildFail("查询失败");
        }
        if(!name.equals("username")  &&!name.equals("Lila")){
            return JsonData.buildFail("没有修改权限,请联系管理员修改");
        }
        //校验参数
        Map<String,Object> editParams=(Map)info.get("info");
        ReturnResult checkResult = checkeditWord(editParams);
        if(!"0".equals(checkResult.getReturnCode())){
            return JsonData.buildFail(checkResult.getReturnMsg());
        }
        Map<String,Object> params =new HashMap<>();
        params.put("id",editParams.get("id"));
        params.put("word",editParams.get("word"));
        params.put("explaination",editParams.get("explaination"));
        params.put("phonetic",editParams.get("phonetic"));
        params.put("wordtype",editParams.get("wordtype"));
        params.put("user",name);
        params.put("sentince",(List)editParams.get("domains"));
        try {
            logger.info("searchWordById查询单个单词信息，参数params",params);
            JsonData result = wordService.editWord (params);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("添加正在需欸单词异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("searcSentences")
    public JsonData searcSentences(HttpServletRequest request,@RequestBody Map<String,String> Params) {
        try {
            String id=Params.get("id");
            Integer pageNum = Params.get("page") == null ? 1 : Integer.parseInt(Params.get("page"));
            Integer pageSize = Params.get("limit") == null ? 20 : Integer.parseInt(Params.get("limit"));
            Map<String,Object> searchParams =new HashMap<>();
            searchParams.put("id",id);
            PageHelper.startPage(pageNum, pageSize);
            logger.info("searcSentences查询例句，参数id", searchParams);
            List<Map<String, Object>> list = wordService.searchSentenceById(searchParams);
            PageInfo pageInfo = new PageInfo(list, pageSize);
            if (list == null) {
                return JsonData.buildFail("Fail,请联系管理员");
            }
            JsonData result = JsonData.buildSuccess(pageInfo);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("查询单词列表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("searchRandomList")
    public JsonData randomList(HttpServletRequest request,@RequestBody Map<String,String> Params) {
        try {
            Integer userId = (Integer) request.getAttribute("user_id");
            if (userId == null) {
                return JsonData.buildFail("查询失败");
            }
            Map<String,Object> searchParams =new HashMap<>();
            searchParams.put("size",Params.get("size"));
            searchParams.put("user_id",userId);
            logger.info("randomList查询随机已背会单词，参数userId", searchParams);
            JsonData result = wordService.selectRandomList(searchParams);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("查询单词列表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    private ReturnResult checkeditWord(Map<String,Object> params){
        ReturnResult result = new ReturnResult();
        if(params == null || params.size() == 0){
            logger.info("editWord参数params为空");
            result.setReturnCode(ReturnCode.param_miss.returnCode);
            result.setReturnMsg("editWord传入的params为空！");
            return result;
        }
        String id=params.get("id").toString();
        String word=params.get("word").toString();
        String explaination=params.get("explaination").toString();
        String phonetic=params.get("phonetic").toString();
        String wordtype=params.get("wordtype").toString();
        if(params.get("domains")==null){
            logger.info("editWord参数sentence为none");
            result.setReturnCode(ReturnCode.info_not_match.returnCode);
            result.setReturnMsg("editWord参数sentence为none");
        }
        //List<Map<String,Object>> sentince = (List)params.get("domains");
        //需要补充例句每个字段的非空校验

        Map<String, String> checkMap = new HashMap();
        checkMap.put("id", id);
        checkMap.put("word", word);
        checkMap.put("explaination", explaination);
        checkMap.put("phonetic", phonetic);
        checkMap.put("wordtype", wordtype);
        result = BaseUtil.parameterNotBankCheck(checkMap);

        return result;
    }


    private ReturnResult checksearchWords(Map<String,String> searchParams) {
        ReturnResult result = new ReturnResult();
        if(searchParams == null || searchParams.size() == 0){
            logger.info("searchWords参数searchParams为空");
            result.setReturnCode(ReturnCode.param_miss.returnCode);
            result.setReturnMsg("searchWords传入的searchParams为空！");
            return result;
        }
        String info=searchParams.get("info");
        Map<String, String> checkMap = new HashMap();
        checkMap.put("info", info);
        result = BaseUtil.parameterNotBankCheck(checkMap);
        return result;
    }



    private ReturnResult checkAddWord(Map<String,String> addParams){
        ReturnResult result = new ReturnResult();
        if(addParams == null || addParams.size() == 0){
            logger.info("AddWord参数addParams为空");
            result.setReturnCode(ReturnCode.param_miss.returnCode);
            result.setReturnMsg("AddWord传入的classInfo为空！");
            return result;
        }
        String number=addParams.get("number");
        String userId=addParams.get("userId");
        //参数非空校验
        Map<String, String> checkMap = new HashMap();
        checkMap.put("number", number);
        result = BaseUtil.parameterNotBankCheck(checkMap);
        if(!StringUtils.isNumeric(number)){
            logger.info("AddWord参数number不是数字,userId:"+userId);
            result.setReturnCode(ReturnCode.info_not_match.returnCode);
            result.setReturnMsg("AddWord参数number不是数字！");
        }
        if(Integer.parseInt(number)>20||Integer.parseInt(number)<1){
            logger.info("AddWord参数number不是1-20之间,userId:"+userId);
            result.setReturnCode(ReturnCode.info_not_match.returnCode);
            result.setReturnMsg("AddWord参数number不是1-20之间！");
        }
        return result;
    }



}
