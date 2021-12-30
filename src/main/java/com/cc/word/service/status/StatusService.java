package com.cc.word.service.status;

import com.cc.word.model.essay.Essay;
import com.cc.word.model.status.StatusGroup;
import com.cc.word.utils.JsonData;

import java.util.List;
import java.util.Map;

public interface StatusService {
    List<StatusGroup> getAllGroups();

}
