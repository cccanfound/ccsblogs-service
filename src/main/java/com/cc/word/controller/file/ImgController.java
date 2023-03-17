package com.cc.word.controller.file;

import com.cc.word.dao.img.ImgDao;
import com.cc.word.service.img.ImgService;
import com.cc.word.utils.FileDfsUtil;
import com.cc.word.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author liyc
 * @date 2020/10/21 17:33
 */
@RestController
@RequestMapping("api/v1/pri/img")
public class ImgController {

    @Value("${prop.upload-img-essay-folder}")
    private String UPLOAD_IMG_ESSAY_FOLDER;


    @Resource
    private FileDfsUtil fileDfsUtil ;
    @Autowired
    private ImgService imgService;


    //向tomcat中直接存入静态文件
    @RequestMapping("uploadEssayImg")
    public Map uploadEssayImg(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        Map map = new HashMap();
        if (file.isEmpty()) {
            map.put("error","文件上传失败");
            return map;
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = UPLOAD_IMG_ESSAY_FOLDER;
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            map.put("error","路径无效");
            return map;
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("location",fileName);
        return map;
    }


    //现已停用，改用uploadEssayImg
    //此方法为向fastDFS上传数据，但试验结果fastDFS移植起来很麻烦，而且无法手动添加文件，最终还是采用了传统静态文件上传
    @RequestMapping("uploadFileFastDFS")
    public Map uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        Map map = new HashMap();
        if (file.isEmpty()) {
            map.put("error", "文件上传失败，文件为空");
            return map;
        }
        String fileName;
        try{
            String path = fileDfsUtil.upload(file) ;
            if (!StringUtils.isEmpty(path)){
                fileName = path ;
            } else {
                map.put("error","上传失败");
                return map;
            }
        } catch (Exception e){
            e.printStackTrace() ;
            map.put("error","服务器异常");
            return map;
        }
        map.put("location",fileName);
        return map;
    }

    /**
     * 文件删除
     * 待补充
     */
    @RequestMapping(value = "/deleteByPathFastDFS", method = RequestMethod.GET)
    public Map deleteByPath (){
        Map map = new HashMap();
        String filePathName = "group1/M00/00/00/wKjObV9Iy-mAZIE9AACGWRCnSVA093.jpg" ;
        fileDfsUtil.deleteFile(filePathName);
        return map ;
    }

    /**
     * 向fastDFS导入图片并同步数据库中的名称
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/syncUploadFiles", method = RequestMethod.GET)
    public String syncUploadFiles () throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        String filepath = "/files";//file文件夹的目录
        //测试数据
        // String filepath = "C:\\Users\\86173\\Desktop\\img";
        File file = new File(filepath);//File类型可以是文件也可以是文件夹
        File[] fileList = file.listFiles();//将该目录下的所有文件放置在一个File类型的数组中
        int logId = imgService.addSyncImgLogs(fileList.length,"img");
        if(logId==0){
            return "同步失败，同步日志新增失败" ;
        }
        for (int j = 0; j < fileList.length; j++) {
            final int i = j;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        File pdf = fileList[i];
                        FileInputStream fileInputStream = null;
                        fileInputStream = new FileInputStream(pdf);
                        String oldName = pdf.getName();
                        MultipartFile multipartFile = new MockMultipartFile(pdf.getName(), pdf.getName(),
                                "application/octet-stream", fileInputStream);
                        String newName = fileDfsUtil.upload(multipartFile);
                        Map<String,Object> param =new HashMap<>();
                        param.put("oldName",oldName);
                        param.put("newName",newName);
                        param.put("newName",newName);
                        param.put("logId",logId);
                        imgService.updateSyncImg(param);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        }
        //判断是否都执行完再释放线程池资源
        fixedThreadPool.shutdown();
        while (!fixedThreadPool.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("线程池中还有任务在处理");
        }
        return "同步完成" ;
    }
}
