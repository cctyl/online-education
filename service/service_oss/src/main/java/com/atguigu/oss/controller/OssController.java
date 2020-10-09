package com.atguigu.oss.controller;


import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")

public class OssController {

    @Autowired
    OssService ossService;

    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadAvatar")
    public R uploadOssFile(MultipartFile file) {

        //将文件上传到oss中
        //并且拿到文件路径
        String url = ossService.uploadAvatar(file);

        return R.ok().data("url",url);
    }

}
