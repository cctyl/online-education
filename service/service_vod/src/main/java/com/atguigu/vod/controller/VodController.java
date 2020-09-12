package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.commonutils.R;
import com.atguigu.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantProperties;
import com.atguigu.vod.utils.InitVodClient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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


    @Autowired
    ConstantProperties constantProperties;

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

    /**
     * 根据视频id获取凭证
     * @param id
     * @return
     */
    @GetMapping("/playAuth/{id}")
    public R getPlayInfo(@PathVariable("id") String id){


        try {
            DefaultAcsClient defaultAcsClient = InitVodClient.initVodClient(constantProperties.getKeyid(), constantProperties.getKeysecret());


            //创建获取视频地址request 和 response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            //给请求添加参数：设置id
            request.setVideoId(id);

            //执行请求后获得响应
            response = defaultAcsClient.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);

        } catch (ClientException e) {
            throw new GuliException(20001,"获取凭证失败:"+ ExceptionUtil.getMessage(e));
        }

    }


}
