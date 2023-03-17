package com.cc.word.dao.music;

import com.cc.word.model.music.Music;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liyc
 * @date 2023/2/2 22:34
 */
@Repository
public interface MusicDao {
    /**
     * 获取歌手列表
     */
    List<Map<String,Object>> getSingersName(Map<String, Object> param);

    int saveMusic(Music music);

    int updateMusic(Music music);

    List<Map<String,Object>> getMusicList(Map<String, Object> param);

    Music getMusicInfoById(String musicId);

    int delImgUrl(String id);

}
