package com.cc.word.dao.dashboard;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liyc
 * @date 2021/5/11 11:50
 */
@Repository
public interface DashboardDao {
    List<Map<String,Object>> selectDashboardList(Map<String, Object> param);
}
