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

}
