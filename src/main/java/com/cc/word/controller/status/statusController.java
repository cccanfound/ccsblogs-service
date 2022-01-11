package com.cc.word.controller.status;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.word.model.status.StatusDefAddVo;
import com.cc.word.model.status.StatusDefUpdateVo;
import com.cc.word.model.status.StatusGroup;
import com.cc.word.model.status.StatusInfoEditVo;
import com.cc.word.service.essay.EssayService;
import com.cc.word.service.status.StatusService;
import com.cc.word.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @RequestMapping("getAllGroups")
    public JsonData getAllGroups(HttpServletRequest request) {
        try {
            List<StatusGroup> data = statusService.getAllGroups();
            return JsonData.buildSuccess(data);
        }
        catch (Exception e) {
            logger.error("查询所有指标类型异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }

    }

    @RequestMapping("getAllStatusDef")
    public JsonData getAllStatusDef(HttpServletRequest request) {
        try {
            List<Map> statusDef = statusService.getAllStatusDef();
            return JsonData.buildSuccess(statusDef);
        }
        catch (Exception e) {
            logger.error("查询所有指标定义异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }

    }

    @Transactional
    @RequestMapping("saveGroupDefRelation")
    public JsonData saveGroupDefRelation(HttpServletRequest request, @RequestBody Map<String,Object> Params) {
        try {
            List<String> defIds = ((List<Object>)Params.get("defIds")).stream().map(String::valueOf).collect(Collectors.toList());
            String groupId = Params.get("groupId").toString();
            if(StringUtils.isBlank(groupId)){
                return JsonData.buildFail("未选择指标大类");
            }
            List<Map> oldDefIds =  statusService.getGroupDefRelation(groupId);
            //对比两集合差集，找出需要添加和删除的数组
            List<String> addList = defIds.stream()
                    .filter(item -> !oldDefIds
                            .stream()
                            .map(e -> e.get("def_id").toString())
                            .collect(Collectors.toList())
                            .contains(item))
                    .collect(Collectors.toList());
            List<String> deleteList = oldDefIds.stream()
                    .map(e -> e.get("def_id").toString())
                    .filter(item -> !defIds
                            .contains(item))
                    .collect(Collectors.toList());
            statusService.addGroupDefRelation(addList,groupId);
            statusService.deleteGroupDefRelation(deleteList,groupId);
            return JsonData.buildSuccess("操作成功");
        }
        catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("修改指标类型所属指标异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }

    }

    @RequestMapping("getGroupDefRelation")
    public JsonData getGroupDefRelation(HttpServletRequest request, @RequestBody Map<String,Object> Params) {
        try {
            String groupId = Params.get("groupId").toString();
            List<Integer> data = statusService.getGroupDefRelation(groupId).stream().map(item->(int)item.get("def_id")).collect(Collectors.toList());
            return JsonData.buildSuccess(data);
        }
        catch (Exception e) {
            logger.error("查询大类对应指标列表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }

    }

    @RequestMapping("getGroupDefRelationInfo")
    public JsonData getGroupDefRelationInfo(HttpServletRequest request, @RequestBody Map<String,Object> Params) {
        try {
            String groupId = Params.get("groupId").toString();
            List<Map> data = statusService.getGroupDefRelation(groupId);
            return JsonData.buildSuccess(data);
        }
        catch (Exception e) {
            logger.error("查询大类对应指标列表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("addStatusDef")
    public JsonData addStatusDef(HttpServletRequest request,@Validated @RequestBody StatusDefAddVo statusDefAddVo) {
        try {
            Map param = new HashMap();
            param.put("name",statusDefAddVo.getName());
            param.put("unit",statusDefAddVo.getUnit());
            param.put("floor",statusDefAddVo.getFloor());
            param.put("ceiling",statusDefAddVo.getCeiling());
            statusService.saveStatusDef(param);
            return JsonData.buildSuccess("添加成功");
        }
        catch (Exception e) {
            logger.error("新增指标定义表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("updateStatusDef")
    public JsonData updateStatusDef(HttpServletRequest request,@Validated @RequestBody StatusDefUpdateVo statusDefUpdateVo) {
        try {
            Map param = new HashMap();
            param.put("id",statusDefUpdateVo.getId());
            param.put("name",statusDefUpdateVo.getName());
            param.put("unit",statusDefUpdateVo.getUnit());
            param.put("floor",statusDefUpdateVo.getFloor());
            param.put("ceiling",statusDefUpdateVo.getCeiling());
            statusService.saveStatusDef(param);
            return JsonData.buildSuccess("修改成功");
        }
        catch (Exception e) {
            logger.error("新增指标定义表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("getStatusDefInfo")
    public JsonData getStatusDefInfo(HttpServletRequest request, @RequestBody Map<String,Object> Params) {
        try {
            String defId = Params.get("defId").toString();
            Map result = statusService.getStatusDefInfo(defId);
            return JsonData.buildSuccess(result);
        }
        catch (Exception e) {
            logger.error("获取指标定义表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }


    @RequestMapping("getStatusInfoOfSingleDef")
    public JsonData getStatusInfoOfSingleDef(HttpServletRequest request, @RequestBody Map<String,Object> Params) {
        try {
            String defId = Params.get("defId").toString();
            List<Map> result = statusService.getStatusInfoOfSingleDef(defId);
            return JsonData.buildSuccess(result);
        }
        catch (Exception e) {
            logger.error("获取指标对应的数据异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("editStatusInfo")
    public JsonData editStatusInfo(HttpServletRequest request,@Validated @RequestBody StatusInfoEditVo statusInfoEditVo) {
        try {
            Map param = new HashMap();
            param.put("id",statusInfoEditVo.getId());
            param.put("status_id",statusInfoEditVo.getStatusId());
            param.put("val",statusInfoEditVo.getVal());
            param.put("create_time",statusInfoEditVo.getCreateTime());
            param.put("location_id",statusInfoEditVo.getLocationId());
            param.put("remark",statusInfoEditVo.getRemark());
            param.put("danger",statusInfoEditVo.getDanger());
            statusService.editStatusInfo(param);
            return JsonData.buildSuccess("编辑成功");
        }
        catch (Exception e) {
            logger.error("新增指标定义表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("deleteStatusInfo")
    public JsonData deleteStatusInfo(HttpServletRequest request,@RequestBody Map<String,Object> Params) {
        try {
            String id = Params.get("id").toString();
            if(StringUtils.isBlank(id)){
                return JsonData.buildFail("删除未传必填id");
            }
            statusService.deleteStatusInfo(id);
            return JsonData.buildSuccess("编辑成功");
        }
        catch (Exception e) {
            logger.error("新增指标定义表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }


    @RequestMapping("getLocation")
    public JsonData getLocation(HttpServletRequest request) {
        try {
            List<Map> data = statusService.getLocation();
            return JsonData.buildSuccess(data);
        }
        catch (Exception e) {
            logger.error("获取检测单位列表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("getStatusIndex")
    public JsonData getStatusIndex(HttpServletRequest request,@RequestBody Map<String,String> Params) {
        try {
            String groupId = Params.get("groupId");
            String defId = Params.get("defId");
            String dangerId = Params.get("dangerId");
            String locationId = Params.get("locationId");
            Map param = new HashMap();
            param.put("groupId",groupId);
            param.put("defId",defId);
            param.put("dangerId",dangerId);
            param.put("locationId",locationId);
            if(StringUtils.isBlank(groupId)&&StringUtils.isBlank(defId)&&StringUtils.isBlank(dangerId)){
                return JsonData.buildFail("大类，指标类型，异常情况至少选择一个");
            }
            List res = statusService.getStatusIndex(param);
            return JsonData.buildSuccess(res);
        }
        catch (Exception e) {
            logger.error("新增指标定义表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

}
