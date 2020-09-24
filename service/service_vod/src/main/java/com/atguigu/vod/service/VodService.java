package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeVideoById(String id);

    void removeVideoByIdList(List<String> videoIdList);


    //TODO 添加一个方法，统计日播放数。获取播放凭证时就调用此方法，每次调用把redis中的key +1，key是 playNum
    void addPlayNum();
}
