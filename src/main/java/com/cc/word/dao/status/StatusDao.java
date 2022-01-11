package com.cc.word.dao.status;

import com.cc.word.model.essay.Essay;
import com.cc.word.model.status.StatusGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StatusDao {
    /**
     * 获取所有的指标分类
     */
    List<StatusGroup> getAllGroups();

    /**
     * 获取所有的指标分类
     */
    List<Map> getAllStatusDef();

    /**
     * 获取类型对应的所有指标
     */
    List<Map> getGroupDefRelation(String groupId);

    /**
     * 新增类型和指标关系
     */
    void saveGroupDefRelation(String groupId,String defId);

    void deleteGroupDefRelation(String groupId,String defId);

    void saveStatusDef(@Param("param") Map param);

    Map getStatusDefInfo(String defId);

    void updateStatusDef(@Param("param") Map param);

    List<Map> getStatusInfoOfSingleDef(String defId);

    void saveStatusInfo(@Param("param") Map param);

    void updateStatusInfo(@Param("param") Map param);

    void deleteStatusInfo(String id);

    List<Map> getLocation();

    List<Map<String,Object>> getStatusIndex(Map param);

}
