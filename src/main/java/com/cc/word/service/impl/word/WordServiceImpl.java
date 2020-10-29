package com.cc.word.service.impl.word;

import com.cc.word.dao.word.WordDao;
import com.cc.word.service.impl.user.UserServiceImpl;
import com.cc.word.service.word.WordService;
import com.cc.word.utils.JsonData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author liyc
 * @date 2020/10/3 22:02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WordServiceImpl implements WordService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private WordDao wordDao;

    @Override
    public List<Map<String,Object>> selectWordList(Map<String, Object> param) {
        return wordDao.selectWordList(param);
    }

    @Override
    public int addWords(Map<String, Object> param) {
        return wordDao.addWords(param);
    }

    @Override
    public int changeWordState(Map<String, Object> param) {
        return wordDao.changeWordState(param);
    }

    @Override
    public List<Map<String,Object>> searchWords(Map<String, Object> param) {
        return wordDao.searchWords(param);
    }

    @Override
    public Map<String,Object> searchWordById(Map<String, Object> param) {
        Map<String,Object> map= wordDao.searchWordById(param);
        //查询例句
        List<Map<String,Object>> list = wordDao.searchSentence(param);
        if(list==null){
            //需要补充日志
            return null;
        }
        map.put("domains",list);
        return map;
    }

    @Override
    public JsonData editWord(Map<String, Object> param) {
        int id=(int)param.get("id");
        String user=param.get("user").toString();
        int data = wordDao.updateWord(param);
        if(data!=1){
            return JsonData.buildFail("保存单词异常");
        }
        //全量更新例句
        wordDao.deleteSection(param);
        List<Map<String,Object>> list = (List)param.get("sentince");
        for (Map map : list) {
            map.put("id",id);
            map.put("user",user);
            int sentData = wordDao.insertSentence(map);
            if(sentData!=1){
                return JsonData.buildFail("保存例句异常");
            }
        }
        return JsonData.buildSuccess("1");
    }

    @Override
    public List<Map<String,Object>> searchSentenceById(Map<String, Object> param) {
        return wordDao.searchSentenceById(param);
    }

    @Override
    public JsonData selectRandomList(Map<String, Object> param) {
        List<Integer> listAll = wordDao.selectMemorizeCount(param);
        if(listAll==null){
            return JsonData.buildFail("还没有已被会单词");
        }
        int listSize = listAll.size();
        int size = Integer.parseInt(param.get("size").toString());
        if(size*2>listSize){
            return JsonData.buildFail("已背会总数不足单次抽查数两倍,不推荐使用抽查功能");
        }
        //取随机该用户已被会单词存入set去重
        HashSet userWordIdList = new HashSet<>();
        while(userWordIdList.size()<size){
            userWordIdList.add(listAll.get((int)(Math.random()*listSize)));
        }
        param.put("userWordIdList",userWordIdList);
        List<Map<String,Object>> list = wordDao.selectRandomList(param);
        if (list == null) {
            return JsonData.buildFail("Fail,请联系管理员");
        }

        return JsonData.buildSuccess(list);
    }

}
