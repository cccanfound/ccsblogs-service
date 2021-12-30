package com.cc.word.dao.img;

import com.cc.word.model.file.SyncFilesLog;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author liyc
 * @date 2021/9/30 15:22
 */
@Repository
public interface ImgDao {
    void addSyncFilesLog(SyncFilesLog syncFilesLog);

    int updateFileName(Map param);

    void updateSyncFilesLog(Map param);
}
