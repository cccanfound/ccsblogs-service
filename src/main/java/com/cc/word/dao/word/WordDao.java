package com.cc.word.dao.word;

import com.cc.word.model.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liyc
 * @date 2020/10/3 23:48
 */
@Repository
public interface WordDao {
    /**
     * 查询单词列表
     */
    //查询单词列表
    List<Map<String,Object>> selectWordList(Map<String, Object> param);
    //添加单词
    int addWords(Map<String, Object> param);
    //改变单词状态(正在学0，背过1，背会2，简单茨3)
    int changeWordState(Map<String, Object> param);
    //查询单词列表
    List<Map<String,Object>> searchWords(Map<String, Object> param);
    //根据id查询单词
    Map<String,Object> searchWordById(Map<String, Object> param);
    //查询单词例句
    List<Map<String,Object>> searchSentence(Map<String, Object> param);
    //修改单词
    int updateWord(Map<String, Object> param);
    //添加例句
    int insertSentence(Map<String, Object> param);
    //删除例句
    int deleteSection(Map<String, Object> param);

    List<Map<String,Object>> searchSentenceById(Map<String, Object> param);

    List<Map<String,Object>> selectRandomList(Map<String, Object> param);

    List<Integer> selectMemorizeCount(Map<String, Object> param);

}
