package com.cc.word.controller.essay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cc.word.model.common.ReturnCode;
import com.cc.word.model.common.ReturnResult;
import com.cc.word.model.essay.Essay;
import com.cc.word.service.essay.EssayService;
import com.cc.word.utils.BaseUtil;
import com.cc.word.utils.JsonData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.cc.word.utils.JsonData.buildSuccess;

@RestController
@RequestMapping("api/v1/pri/essay")
public class EssayController {
    private static Logger logger = LoggerFactory.getLogger(EssayController.class);

    @Autowired
    private EssayService essayService;

    @RequestMapping("addEssay")
    public JsonData addEssay(HttpServletRequest request, @RequestBody Map<String,String> Params) {
        Integer userId = (Integer) request.getAttribute("user_id");
        if(userId==null){
            return JsonData.buildFail("无登录信息，请重新登陆");
        }
        //参数校验
        ReturnResult checkResult = checkAddEssay(Params);
        if(!"0".equals(checkResult.getReturnCode())){
            return JsonData.buildFail(checkResult.getReturnMsg());
        }
        /*if(Params.get("content").length()>20000){
            return JsonData.buildFail("文章超长，长度请限制为2w以内");
        }*/
        Essay essay=new Essay();
        essay.setAuthorId(userId);
        essay.setTitle(Params.get("title"));
        essay.setContent(Params.get("content"));
        essay.setType(Params.get("type"));
        JsonData data = essayService.addEssay(essay);
        return data;
    }

    @RequestMapping("updateEssay")
    public JsonData updateEssay(HttpServletRequest request, @RequestBody Map<String,String> Params) {
        Integer userId = (Integer) request.getAttribute("user_id");
        if(userId==null){
            return JsonData.buildFail("无登录信息，请重新登陆");
        }
        //参数校验
        ReturnResult checkResult = checkAddEssay(Params);
        if(!"0".equals(checkResult.getReturnCode())){
            return JsonData.buildFail(checkResult.getReturnMsg());
        }
        /*if(Params.get("content").length()>20000){
            return JsonData.buildFail("文章超长，长度请限制为2w以内");
        }*/
        Essay essay=new Essay();
        essay.setId(Integer.parseInt(Params.get("id")));
        essay.setTitle(Params.get("title"));
        essay.setContent(Params.get("content"));
        essay.setType(Params.get("type"));
        essay.setPicture(Params.get("picture"));
        JsonData data = essayService.updateEssay(essay);
        return data;
    }

    @RequestMapping("getCatalog")
    public JsonData getCatalog(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("user_id");
        if(userId==null){
            return JsonData.buildFail("失败。请联系管理员");
        }
        String catalog = essayService.getCatalog(userId);
        if(StringUtils.isBlank(catalog)){
            List<Map> list = (List) JSONObject.parse("[{ 'label': '还没有文章喔' }]");
            return JsonData.buildSuccess(list);
        }
        List<Map> list = (List) JSONObject.parse(catalog);
        return JsonData.buildSuccess(list);
    }

    @RequestMapping("updateCatalog")
    public JsonData updateCatalog(HttpServletRequest request, @RequestBody Map<String,Object> Params) {
        Integer userId = (Integer) request.getAttribute("user_id");
        if(userId==null){
            return JsonData.buildFail("无登录信息，请重新登陆");
        }
        String catalog = JSON.toJSONString(Params.get("data"));
        if(StringUtils.isBlank(catalog)){
            return JsonData.buildFail("更新目录失败,传入目录为空");
        }
        Map param = new HashMap();
        param.put("user_id",userId);
        param.put("catalog",catalog);
        int data = essayService.updateCatalog(param);
        if(data!=1){
            return JsonData.buildFail("更新目录失败。请联系管理员");
        }
        return JsonData.buildSuccess("更新成功");
    }


    @RequestMapping("delEssay")
    public JsonData delEssay(HttpServletRequest request, @RequestBody Map<String,Object> Params) {
        Integer userId = (Integer) request.getAttribute("user_id");
        if(userId==null){
            return JsonData.buildFail("无登录信息，请重新登陆");
        }
        String catalog = JSON.toJSONString(Params.get("data"));
        String id = Params.get("id").toString();
        if(StringUtils.isBlank(catalog)){
            return JsonData.buildFail("更新目录失败,传入目录为空");
        }
        Map param = new HashMap();
        param.put("user_id",userId);
        param.put("essay_id",id);
        param.put("catalog",catalog);
        JsonData data = essayService.delEssay(param);
        return data;
    }

    @RequestMapping("searchEssay")
    public JsonData searchEssay(@RequestBody Map<String,String> Params) {
        String info = Params.get("info");
        if(StringUtils.isBlank(info)){
            return JsonData.buildFail("查询失败,传入搜索条件为空");
        }
        List<Map> titleList = essayService.essayTitleList(Params.get("info"));
        if(titleList == null){
            return JsonData.buildFail("失败。请联系管理员");
        }
        return JsonData.buildSuccess(titleList);
    }

    private ReturnResult checkAddEssay(Map<String,String> params){
        ReturnResult result = new ReturnResult();
        if(params == null || params.size() == 0){
            logger.info("AddEssay参数params为空");
            result.setReturnCode(ReturnCode.param_miss.returnCode);
            result.setReturnMsg("AddEssay传入的params为空！");
            return result;
        }
        String title=params.get("title");
        String content=params.get("content");
        String type=params.get("type");

        Map<String, String> checkMap = new HashMap();
        checkMap.put("type", type);
        checkMap.put("content", content);
        checkMap.put("title", title);
        result = BaseUtil.parameterNotBankCheck(checkMap);

        return result;
    }

}
