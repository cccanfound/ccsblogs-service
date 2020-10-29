package com.cc.word.service.essay;

import com.cc.word.model.essay.Essay;
import com.cc.word.utils.JsonData;

import java.util.List;
import java.util.Map;

public interface EssayService {
    JsonData addEssay(Essay essay);

    Essay essayList(int user_id);

    String getCatalog(int user_id);

    int updateCatalog(Map catalogInfo);

    JsonData updateEssay(Essay essay);

    JsonData delEssay(Map info);

    List<Map> essayTitleList(String info);
}
