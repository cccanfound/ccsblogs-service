package com.cc.word.service.impl.music;

import com.cc.word.dao.music.MusicDao;
import com.cc.word.model.music.Music;
import com.cc.word.service.music.MusicService;
import com.cc.word.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MusicServiceImpl implements MusicService {
    private static Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class);

    @Autowired
    private MusicDao musicDao;

    @Override
    public List<Map<String, Object>> querySinger(Map<String, Object> param) {
        List<Map<String,Object>> data = musicDao.getSingersName(param);
        return data;
    }
    @Override
    public int addMusic(Music music){
        return musicDao.saveMusic(music);
    }

    @Override
    public int updateMusic(Music music) {
        return musicDao.updateMusic(music);
    }

    @Override
    public List<Map<String, Object>> queryMusicList(Map<String, Object> param) {
        List<Map<String,Object>> data = musicDao.getMusicList(param);
        return data;
    }
    @Override
    public Music getMusicInfoById(String musicId){
        Music music = musicDao.getMusicInfoById(musicId);
        return music;
    }

    @Override
    public int delImgUrl(String id) {
        return musicDao.delImgUrl(id);
    }
}
