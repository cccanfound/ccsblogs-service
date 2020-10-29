package com.cc.word.service.impl.essay;

import com.alibaba.fastjson.JSON;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.cc.word.dao.essay.EssayDao;
import com.cc.word.model.essay.Essay;
import com.cc.word.service.essay.EssayService;
import com.cc.word.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class EssayServiceImpl implements EssayService {
    private static Logger logger = LoggerFactory.getLogger(EssayServiceImpl.class);

    @Autowired
    private EssayDao essayDao;

    @Override
    public JsonData addEssay(Essay essay) {
        try{
            essay.setCreateTime(new Date());
            int data = essayDao.saveEssay(essay);
            int id=essay.getId();
            if(data!=1){
                return JsonData.buildError("保存文章错误，请联系管理员");
            }
            String catalog = essayDao.selectUserEssayCatalog(essay.getAuthorId());
            Map map = new HashMap<>();
            map.put("label",essay.getTitle());
            map.put("id",id);
            if(StringUtils.isBlank(catalog)){
                List list = new ArrayList();
                list.add(map);
                catalog = JSON.toJSONString(list);
            } else{
                catalog="["+JSON.toJSONString(map)+","+ catalog.substring(1);
            }
            Map param = new HashMap();
            param.put("catalog",catalog);
            param.put("user_id",essay.getAuthorId());
            int CatalogData = essayDao.updateCatalog(param);
            if(CatalogData!=1){
                return JsonData.buildError("保存目录错误，请联系管理员");
            }
            return JsonData.buildSuccess("文章保存成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        logger.error("查询单词列表异常，原因为:[{}]", e.getMessage(), e);
        return JsonData.buildError("运行错误，请联系管理员");
    }

    }

    @Override
    public Essay essayList(int essay_id) {
        return essayDao.essayList(essay_id);
    }

    @Override
    public String getCatalog(int user_id) {
        return essayDao.selectUserEssayCatalog(user_id);
    }

    @Override
    public int updateCatalog(Map catalogInfo) {
        return essayDao.updateCatalog(catalogInfo);
    }

    @Override
    public JsonData updateEssay(Essay essay) {
        int essayData = essayDao.updateEssay(essay);
        if(essayData!=1){
            return JsonData.buildError("更新文章错误，请联系管理员");
        }
        return JsonData.buildSuccess("文章更新成功");
    }

    @Override
    public JsonData delEssay(Map info) {
        try{
            Map catalogMap = new HashMap<>();
            catalogMap.put("user_id",info.get("user_id"));
            catalogMap.put("catalog",info.get("catalog"));
            int re = essayDao.updateCatalog(catalogMap);
            Map essayMap = new HashMap<>();
            essayMap.put("id",info.get("essay_id"));
            int re1 =  essayDao.delEssay(essayMap);
            if(re==1 && re1==1){
                return JsonData.buildSuccess("删除成功");
            }
            else{
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return JsonData.buildFail("删除失败，请联系管理员");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("删除文章异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }

    }

    @Override
    public List<Map> essayTitleList(String info) {
        return essayDao.searchEssayTitleList(info);
    }

}
