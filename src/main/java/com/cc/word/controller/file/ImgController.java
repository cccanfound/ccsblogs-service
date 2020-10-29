package com.cc.word.controller.file;

import com.cc.word.utils.JsonData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liyc
 * @date 2020/10/21 17:33
 */
@RestController
@RequestMapping("api/v1/pub/img")
public class ImgController {
    @Value("${prop.upload-img-folder}")
    private String UPLOAD_IMG_FOLDER;
    @RequestMapping("upload")
    public Map addEssay(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        Map map = new HashMap();
        if (file.isEmpty()) {
            map.put("error","文件上传失败");
            return map;
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = UPLOAD_IMG_FOLDER;
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
}
