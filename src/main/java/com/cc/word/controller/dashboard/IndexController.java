package com.cc.word.controller.dashboard;

import com.cc.word.controller.word.WordController;
import com.cc.word.model.common.ReturnResult;
import com.cc.word.model.essay.Essay;
import com.cc.word.service.dashboard.DashboardService;
import com.cc.word.service.word.WordService;
import com.cc.word.utils.JsonData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

/**
 * @author liyc
 * @date 2021/5/11 11:38
 */

@RestController
@RequestMapping("api/v1/pub/dashboard")
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping("list")
    public JsonData list(@RequestBody Map<String,String> Params) {
        try {
            Integer pageNum = Params.get("page") == null ? 1 : Integer.parseInt(Params.get("page"));
            Integer pageSize = Params.get("limit") == null ? 20 : Integer.parseInt(Params.get("limit"));
            Map<String,Object> searchParams =new HashMap<>();
            PageHelper.startPage(pageNum, pageSize);
            logger.info("selectUser查询用户名是否以及存在，参数userId,type", searchParams);
            List<Map<String, Object>> list = dashboardService.selectIndexList(searchParams);
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

}
