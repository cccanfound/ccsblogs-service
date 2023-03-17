package com.cc.word.service.music;

import com.cc.word.model.music.Music;
import com.cc.word.utils.JsonData;

import java.util.List;
import java.util.Map;

/**
 * @author liyc
 * @date 2023/2/2 21:16
 */
public interface MusicService {
    List<Map<String,Object>> querySinger(Map<String, Object> param);

    int addMusic(Music music);

    int updateMusic(Music music);

    List<Map<String,Object>> queryMusicList(Map<String, Object> param);

    Music getMusicInfoById(String musicId);

    int delImgUrl(String id);
}
