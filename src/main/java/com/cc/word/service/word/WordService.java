package com.cc.word.service.word;

import com.cc.word.utils.JsonData;

import java.util.List;
import java.util.Map;

/**
 * @author liyc
 * @date 2020/10/3 21:55
 */
public interface WordService {
    List<Map<String,Object>> selectWordList(Map<String, Object> param);

    int addWords(Map<String, Object> param);

    int changeWordState(Map<String, Object> param);

    List<Map<String,Object>> searchWords(Map<String, Object> param);

    Map<String,Object> searchWordById(Map<String, Object> param);

    JsonData editWord(Map<String, Object> param);

    List<Map<String,Object>> searchSentenceById(Map<String, Object> param);

    JsonData selectRandomList(Map<String, Object> param);



}
