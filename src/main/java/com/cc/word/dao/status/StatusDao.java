package com.cc.word.dao.status;

import com.cc.word.model.essay.Essay;
import com.cc.word.model.status.StatusGroup;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StatusDao {
    /**
     * 获取所有的指标分类
     */
    List<StatusGroup> getAllGroups();

}
