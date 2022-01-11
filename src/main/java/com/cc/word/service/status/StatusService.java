package com.cc.word.service.status;

import com.cc.word.model.essay.Essay;
import com.cc.word.model.status.StatusGroup;
import com.cc.word.utils.JsonData;

import java.util.List;
import java.util.Map;

public interface StatusService {
    List<StatusGroup> getAllGroups();

    List<Map> getAllStatusDef();

    List<Map> getGroupDefRelation(String groupId);

    void addGroupDefRelation(List<String> list,String groupId);

    void deleteGroupDefRelation(List<String> list,String groupId);

    void saveStatusDef(Map param);

    Map getStatusDefInfo(String defId);

    List<Map> getStatusInfoOfSingleDef(String defId);

    void editStatusInfo(Map param);

    void deleteStatusInfo(String id);

    List<Map> getLocation();

    List<Map> getStatusIndex(Map param);
}
