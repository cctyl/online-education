package com.atguigu.order.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weixin.pay")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxPayProperties {

    private String appid;
    private String partner;
    private String partnerkey;
    private String notifyurl;
    private String qrurl;
    private String orderqueryurl;

}
