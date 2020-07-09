package com.atguigu.oss.service.impl;

import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantProperties;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Autowired
    ConstantProperties constantProperties;


    /**
     * 上传头像到oss中，返回头像url
     *
     * @param file
     * @return
     */
    @Override
    public String uploadAvatar(MultipartFile file) {


        String basePath ="https://"+ constantProperties.getBucketname()+"."+constantProperties.getEndpoint()+"/";
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = constantProperties.getEndpoint();
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = constantProperties.getKeyid();
        String accessKeySecret = constantProperties.getKeysecret();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //获取文件名,并且加上随机值
        String originalFilename =UUID.randomUUID().toString().replaceAll("-","")+ file.getOriginalFilename();

        //对文件进行分类存放，按照日期进行分类
        String today = new DateTime().toString("yyyy/MM/dd");
        String realPath =today+ "/"+originalFilename;
        log.debug("上传了一个文件："+originalFilename);

        try {
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            // 第一个参数  bucketname
            //第二个参数   文件名和路径
            ossClient.putObject(constantProperties.getBucketname(), realPath, inputStream);
        } catch (IOException e) {

            log.error(ExceptionUtil.getMessage(e));
            return null;
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        //拼接路径
        // https://bucketname.endpoint/文件路径
        String url = basePath+realPath;

        return url;
    }
}
