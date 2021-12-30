package com.cc.word.controller.status;

import com.alibaba.fastjson.JSONObject;
import com.cc.word.model.status.StatusGroup;
import com.cc.word.service.essay.EssayService;
import com.cc.word.service.status.StatusService;
import com.cc.word.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author liyc
 * @date 2021/12/29 17:06
 */
@RestController
@RequestMapping("api/v1/pri/status")
public class statusController {
    private static Logger logger = LoggerFactory.getLogger(statusController.class);

    @Autowired
    private StatusService statusService;


    /*@RequestMapping("getStatusGroup")
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
    }*/

    @RequestMapping("getAllGroups")
    public JsonData getAllGroups(HttpServletRequest request) {
        try {
            List<StatusGroup> catalog = statusService.getAllGroups();
            return JsonData.buildSuccess(catalog);
        }
        catch (Exception e) {
            logger.error("查询所有指标类型异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }

    }

}
