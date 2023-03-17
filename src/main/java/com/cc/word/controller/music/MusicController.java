package com.cc.word.controller.music;

import com.cc.word.model.music.Music;
import com.cc.word.model.music.MusicInfoVo;
import com.cc.word.model.status.StatusDefAddVo;
import com.cc.word.service.music.MusicService;
import com.cc.word.utils.JsonData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author liyc
 * @date 2023/2/2 21:12
 */
@RestController
@RequestMapping("api/v1/pri/music")
public class MusicController {
    private static Logger logger = LoggerFactory.getLogger(MusicController.class);

    @Value("${prop.upload-img-music-folder}")
    private String UPLOAD_IMG_MUSIC_FOLDER;

    @Autowired
    private MusicService musicService;

    @RequestMapping("querySinger")
    public JsonData querySinger(@RequestBody Map<String, String> Params) {
        try {
            String singerName = Params.get("singer_name");
            //需要查询的数据的最大条数
            int count = Integer.parseInt(Params.get("count"));
            if (singerName == null || singerName.equals("")) {
                return JsonData.buildFail("输入信息为空");
            }
            Map<String, Object> searchParams = new HashMap<>();
            searchParams.put("singerName", singerName);
            searchParams.put("count", count);
            logger.info("querySinger查询歌手信息，singerName，count", searchParams);
            List<Map<String, Object>> list = musicService.querySinger(searchParams);
            JsonData result = JsonData.buildSuccess(list);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("查询歌手信息异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("mergeMusicInfo")
    public JsonData mergeMusicInfo(HttpServletRequest request, @Validated @RequestBody MusicInfoVo musicInfoVo) {
        try {
            String id = musicInfoVo.getId();
            String musicName = musicInfoVo.getMusicName();
            String singerId = musicInfoVo.getSingerId();
            String describe = musicInfoVo.getDescribe();
            String musicImg = musicInfoVo.getMusicImg();
            String musicUrl = musicInfoVo.getMusicUrl();
            String lyricUrl = musicInfoVo.getLyricUrl();
            Music music = new Music();
            music.setMusicName(musicName);
            music.setSingerId(singerId);
            //新增
            if (id == null || id == "" || id.isEmpty()) {
                music.setCreateTime(new Date());
                music.setEditUserId(request.getAttribute("user_id").toString());
                logger.info("mergeMusicInfo添加歌曲信息", music.toString());
                if (musicService.addMusic(music) != 1) {
                    return JsonData.buildError("添加音乐信息失败，请联系管理员");
                }
                id = music.getId();
                Map map = new HashMap<>();
                map.put("id", id);
                return JsonData.buildSuccess(map);
            }
            //更新
            music.setId(id);
            music.setMusicImg(musicImg);
            music.setMusicUrl(musicUrl);
            music.setLyricUrl(lyricUrl);
            music.setEditTime(new Date());
            music.setEditUserId(request.getAttribute("user_id").toString());
            logger.info("mergeMusicInfo更新歌曲信息", music.toString());
            if (musicService.updateMusic(music) != 1) {
                return JsonData.buildError("更新音乐信息失败，请联系管理员");
            }
            Map map = new HashMap<>();
            map.put("id", id);
            return JsonData.buildSuccess(map);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("mergeMusicInfo异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("queryMusicList")
    public JsonData queryMusicList(@RequestBody Map<String, String> Params) {
        try {
            String searchInfo = Params.get("searchInfo");
            //需要查询的数据的最大条数
            int count = Integer.parseInt(Params.get("count"));
            Map<String, Object> searchParams = new HashMap<>();
            searchParams.put("searchInfo", searchInfo);
            searchParams.put("count", count);
            logger.info("queryMusicList查询歌曲列表，searchInfo，count", searchParams);
            List<Map<String, Object>> list = musicService.queryMusicList(searchParams);
            JsonData result = JsonData.buildSuccess(list);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("查询歌曲列表异常，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    @RequestMapping("querySingleMusic")
    public JsonData querySingleMusic(@RequestBody Map<String, String> Params) {
        try {
            String musicId = Params.get("musicId");
            if (musicId == null || musicId.equals("")) {
                return JsonData.buildFail("musicId不能为空");
            }
            logger.info("querySingleMusic查询歌曲信息，musicId", musicId);
            Music music = musicService.getMusicInfoById(musicId);
            JsonData result = JsonData.buildSuccess(music);
            return result;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("查询歌曲信息，原因为:[{}]", e.getMessage(), e);
            return JsonData.buildError("运行错误，请联系管理员");
        }
    }

    //上传歌曲封面
    @RequestMapping("uploadCoverImg")
    public JsonData uploadCoverImg(HttpServletRequest request,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam("id") String id) {
        Map map = new HashMap();
        if (file.isEmpty()) {
            return JsonData.buildFail("文件上传失败");
        }
        String originalFileName = file.getOriginalFilename();  // 文件名
        String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));  // 后缀名
        String filePath = UPLOAD_IMG_MUSIC_FOLDER;
        String fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            return JsonData.buildFail("路径无效");
        }
        try {
            file.transferTo(dest);
            Music music = new Music();
            music.setId(id);
            music.setMusicImg(UPLOAD_IMG_MUSIC_FOLDER + fileName);
            music.setEditTime(new Date());
            music.setEditUserId(request.getAttribute("user_id").toString());
            logger.info("uploadCoverImg更新歌曲更面", music.toString());
            musicService.updateMusic(music);
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("location", UPLOAD_IMG_MUSIC_FOLDER + fileName);
        map.put("originalFileName", originalFileName);
        JsonData result = JsonData.buildSuccess(map);
        return result;
    }

    //删除歌曲封面
    @RequestMapping("deleteImg")
    public JsonData deleteImg(@RequestBody Map<String,String> Params) {
        String filePath=Params.get("filepath");
        //歌曲id
        String id=Params.get("id");
        if (filePath == null||filePath.equals("")) {
            return JsonData.buildFail("删除文件路径为空");
        }
        File file = new File(filePath);
        //路径是个文件且不为空时删除文件
        if(file.isFile() && file.exists()){
            //删除文件
            if(!file.delete()){
                return JsonData.buildFail("删除失败");
            }
            //删除数据库中对应字段
            musicService.delImgUrl(id);
        }
        else{
            return JsonData.buildFail("没找到该文件");
        }
        JsonData result = JsonData.buildSuccess();
        return result;
    }
}
