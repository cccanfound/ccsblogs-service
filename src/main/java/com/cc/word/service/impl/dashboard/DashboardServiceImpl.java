package com.cc.word.service.impl.dashboard;

import com.cc.word.dao.dashboard.DashboardDao;
import com.cc.word.service.dashboard.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liyc
 * @date 2021/5/11 11:46
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardDao dashboardDao;

    @Override
    public List<Map<String, Object>> selectIndexList(Map<String, Object> param) {
        return dashboardDao.selectDashboardList(param);
    }
}
