package com.cc.word.service.impl.img;

import com.cc.word.dao.img.ImgDao;
import com.cc.word.model.file.SyncFilesLog;
import com.cc.word.service.img.ImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ImgServiceImpl implements ImgService {
    private static Logger logger = LoggerFactory.getLogger(ImgServiceImpl.class);

    @Autowired
    private ImgDao imgDao;

    @Override
    public int addSyncImgLogs(int total,String type) {
        try{
            SyncFilesLog syncFilesLog = new SyncFilesLog();
            syncFilesLog.setTime(new Date());
            syncFilesLog.setTotal(total);
            syncFilesLog.setType(type);
            syncFilesLog.setSuccess(0);
            syncFilesLog.setFail(0);
            imgDao.addSyncFilesLog(syncFilesLog);
            int id=syncFilesLog.getId();
            return id;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("新增文件同步日志表异常，原因为:[{}]", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public void updateSyncImg(Map param) {
        Map map = new HashMap();
        int data = imgDao.updateFileName(param);
        //更新同步日志表sync_files_log
        map.put("id",param.get("logId"));
        if(data!=1){
            map.put("successOrFail","fail");
            imgDao.updateSyncFilesLog(map);
        }
        else{
            map.put("successOrFail","success");
            imgDao.updateSyncFilesLog(map);
        }
    }


}
