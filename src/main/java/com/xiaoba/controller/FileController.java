package com.xiaoba.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author zhouning
 */
@Controller
@RequestMapping("/file/")
public class FileController {

    Logger logger = (Logger) LoggerFactory.getLogger(getClass());
    @Value("${filepath}")
    private String filepath;

    @RequestMapping("/")
    public String index(){
        return "upload";
    }


    @RequestMapping("upload")
    @ResponseBody
    public String upload (@RequestParam("file") MultipartFile file) {
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 获取后缀名
        // String suffixName = fileName.substring(fileName.lastIndexOf("."));

        // 文件重命名，防止重复
        //fileName = filepath + UUID.randomUUID() + fileName;
        fileName = filepath + fileName;
        System.out.println(fileName);
        logger.info("托盘存放位置"+fileName);

        // 文件对象
        File dest = new File(fileName);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            return "http://39.99.203.80:8081/images/"+file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

}
