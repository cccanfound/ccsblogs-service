package com.cc.word.dao.essay;

import com.cc.word.model.essay.Essay;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EssayDao {
    /**
     * 保存文章
     */
    int saveEssay(Essay essay);
    /**
     * 根据id搜索文章
     */
    Essay essayList(int essay_id);
    /**
     * 查询用户的目录
     */
    String selectUserEssayCatalog(int user_id);
    /**
     * 保存目录
     */
    int updateCatalog(Map param);
    /**
     * 更新文章
     */
    int updateEssay(Essay essay);
    /**
     * 删除文章
     */
    int delEssay(Map param);
    /**
     * 查询文章题目列表
     */
    List<Map> searchEssayTitleList(String param);

}
