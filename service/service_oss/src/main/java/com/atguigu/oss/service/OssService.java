package com.atguigu.oss.service;


import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    /**
     * 上传头像到oss中，返回头像url
     * @param file
     * @return
     */
    String uploadAvatar(MultipartFile file);
}
