package com.cc.word.controller.essay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.word.model.common.ReturnCode;
import com.cc.word.model.common.ReturnResult;
import com.cc.word.model.essay.Essay;
import com.cc.word.service.essay.EssayService;
import com.cc.word.utils.BaseUtil;
import com.cc.word.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pub/essay")
public class PubEssayController {
    private static Logger logger = LoggerFactory.getLogger(PubEssayController.class);

    @Autowired
    private EssayService essayService;


    @RequestMapping("getEssay")
    public JsonData getEssay(@RequestBody Map<String,String> Params) {

        Essay essay = essayService.essayList(Integer.parseInt(Params.get("essayId")));
        if(essay == null){
            return JsonData.buildFail("失败。请联系管理员");
        }
        return JsonData.buildSuccess(essay);
    }
}
