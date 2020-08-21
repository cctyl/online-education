package com.atguigu.educenter.config;


import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wx.open")
@Data
public class WxConfigProperties   {

    private String appId;
    private String appSecret;
    private String redirectUrl;



}
