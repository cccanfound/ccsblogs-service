package com.cc.word.service.img;


import java.util.Map;

public interface ImgService {

    /**
     * 创建同步日志记录
     * @param total
     * @param type
     */
    int addSyncImgLogs(int total,String type);

    /**
     * 同步图片（将导入图片原数据库中的名字替换成新的）
     * 更新同步日志表中的记录(失败数指的是存入fastDFS了但是数据库中原本没有该图片。无法替换名称)
     * @param param
     */
    void updateSyncImg(Map param);

}
