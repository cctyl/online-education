package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
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

    /**
     * 根据id删除阿里云中的视频
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除视频")
    public R removeVideoById(@ApiParam("id") @PathVariable("id") String id){

        vodService.removeVideoById(id);
        return R.ok();

    }

    /**
     * 根据id列表删除视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/deleteBatch")
    public R removeVideoByIdList(@RequestParam("videoIdList")List<String> videoIdList){


        vodService.removeVideoByIdList(videoIdList);
        return R.ok();

    }

}
