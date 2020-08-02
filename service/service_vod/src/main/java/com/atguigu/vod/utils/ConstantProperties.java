package com.atguigu.vod.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 常量类
 * 用于读取配置文件中的数据
 */
@Component
@ConfigurationProperties(prefix = "aliyun.vod.file")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstantProperties {


    private String keyid;
    private String keysecret;



}
