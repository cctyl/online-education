package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class VodController {

    @Autowired
    VodService vodService;

    /**
     * 将视频上传到阿里云，返回一个视频id
     * @param file 文件流
     * @return 视频id
     */
    @PostMapping("/upload")
    @ApiOperation("视频上传")
    public R uploadVideo(@ApiParam("文件") MultipartFile file){

        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);

    }

}
