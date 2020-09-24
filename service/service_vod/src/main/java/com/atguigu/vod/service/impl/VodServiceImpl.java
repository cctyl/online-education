package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.commonutils.RedisUtils;
import com.atguigu.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantProperties;

import com.atguigu.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class VodServiceImpl implements VodService {

    @Autowired
    ConstantProperties constantProperties;

    @Autowired
    RedisUtils redisUtils;


    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            UploadStreamRequest request = null;
            //title和filename可以一样

            String fileName = file.getOriginalFilename();
            String title =fileName.substring(0,fileName.lastIndexOf("."));  //不要文件后缀

            request = new UploadStreamRequest(constantProperties.getKeyid(), constantProperties.getKeysecret(),
                    title, fileName, file.getInputStream());


            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = "";
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                videoId = response.getVideoId();
            }

            return videoId;
        } catch (Exception e) {

            throw new GuliException(20001,ExceptionUtil.getMessage(e));
        }
    }

    @Override
    public void removeVideoById(String id) {


        try {
            DefaultAcsClient defaultAcsClient = InitVodClient.initVodClient(constantProperties.getKeyid(), constantProperties.getKeysecret());
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            defaultAcsClient.getAcsResponse(request);

        } catch (ClientException e) {

           throw new GuliException(20001,"视频不存在"+ExceptionUtil.getMessage(e));
        }


    }

    @Override
    public void removeVideoByIdList(List<String> videoIdList) {
        try {
            DefaultAcsClient defaultAcsClient = InitVodClient.initVodClient(constantProperties.getKeyid(), constantProperties.getKeysecret());
            DeleteVideoRequest request = new DeleteVideoRequest();

            String join = StringUtils.join(videoIdList.toArray(), ",");

            request.setVideoIds(join);
            defaultAcsClient.getAcsResponse(request);

        } catch (ClientException e) {

            throw new GuliException(20001,ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 播放时调用此方法，使播放数加一
     */
    @Override
    public void addPlayNum() {

        String playNumStr = redisUtils.get("playNum");
        if (StringUtils.isEmpty(playNumStr)){
            playNumStr = "0";
        }

        int count = Integer.parseInt(playNumStr);
        count++;
        redisUtils.set("playNum",count+"");



    }


}
