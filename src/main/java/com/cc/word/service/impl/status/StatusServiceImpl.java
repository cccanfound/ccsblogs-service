package com.cc.word.service.impl.status;

import com.alibaba.fastjson.JSON;
import com.cc.word.dao.essay.EssayDao;
import com.cc.word.dao.status.StatusDao;
import com.cc.word.model.essay.Essay;
import com.cc.word.model.status.StatusGroup;
import com.cc.word.service.essay.EssayService;
import com.cc.word.service.status.StatusService;
import com.cc.word.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class StatusServiceImpl implements StatusService {
    private static Logger logger = LoggerFactory.getLogger(StatusServiceImpl.class);

    @Autowired
    private StatusDao statusDao;

    @Override
    public List<StatusGroup> getAllGroups() {
        return statusDao.getAllGroups();
    }
    @Override
    public List<Map> getAllStatusDef(){
        return statusDao.getAllStatusDef();
    }

    @Override
    public List<Map> getGroupDefRelation(String groupId){
        return statusDao.getGroupDefRelation(groupId);
    }

    @Override
    public void addGroupDefRelation(List<String> list,String groupId){
        list.forEach(item->{
            statusDao.saveGroupDefRelation(groupId,item);
        });
    }

    @Override
    public void deleteGroupDefRelation(List<String> list,String groupId){
        list.forEach(item->{
            statusDao.deleteGroupDefRelation(groupId,item);
        });
    }

    @Override
    public void saveStatusDef(Map param){
        if(param.get("id")==null||StringUtils.isBlank(param.get("id").toString())){
            statusDao.saveStatusDef(param);
        }
        else {
            statusDao.updateStatusDef(param);
        }
    }

    @Override
    public Map getStatusDefInfo(String defId){
        return statusDao.getStatusDefInfo(defId);
    }
    @Override
    public List<Map> getStatusInfoOfSingleDef(String defId){
        return statusDao.getStatusInfoOfSingleDef(defId);
    }
    @Override
    public void editStatusInfo(Map param){
        if(param.get("id")==null||StringUtils.isBlank(param.get("id").toString())){
            statusDao.saveStatusInfo(param);
        }
        else {
            statusDao.updateStatusInfo(param);
        }
    }
    @Override
    public void deleteStatusInfo(String id){
        statusDao.deleteStatusInfo(id);
    }


    @Override
    public List<Map> getLocation(){
        return statusDao.getLocation();
    }

    @Override
    public List<Map> getStatusIndex(Map param){
        List<Map<String,Object>> data = statusDao.getStatusIndex(param);
        List result = new ArrayList();
        int parentDefId = 0;
        Map<String,Object> parentMap = new HashMap<>();
        for(int i = 0;i<data.size();i++){
            if(data.get(i).get("defId")==null) continue;
            if((int)data.get(i).get("defId")==parentDefId){
                ((List)parentMap.get("children")).add(data.get(i));
            }
            else {
                if(parentMap.size()!=0) result.add(parentMap);
                parentDefId = (int)data.get(i).get("defId");
                parentMap = data.get(i);
                parentMap.put("children",new ArrayList<>());
            }
        }
        if(parentMap.size()!=0) result.add(parentMap);
        return result;
    }

}
