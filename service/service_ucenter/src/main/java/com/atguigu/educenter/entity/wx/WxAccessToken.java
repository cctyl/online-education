package com.atguigu.educenter.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxAccessToken {

    private String access_token;

    private int expires_in;

    private String refresh_token;

    private String openid;

    private String scope;

    private String unionid;
}
