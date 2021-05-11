package com.cc.word.service.dashboard;

import java.util.List;
import java.util.Map;

/**
 * @author liyc
 * @date 2021/5/11 11:44
 */
public interface DashboardService {
    List<Map<String,Object>> selectIndexList(Map<String, Object> param);
}
