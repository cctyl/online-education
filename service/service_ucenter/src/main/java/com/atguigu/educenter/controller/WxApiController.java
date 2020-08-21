package com.atguigu.educenter.controller;

import com.atguigu.commonutils.RedisUtils;
import com.atguigu.educenter.config.WxConfigProperties;
import com.atguigu.educenter.entity.wx.WxAccessToken;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.exceptionhandler.GuliException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
@Slf4j
public class WxApiController {

    @Autowired
    WxConfigProperties wxConfigProperties;


    @Autowired
    RestTemplate restTemplate;


    @Autowired
    RedisUtils redisUtils;

    /**
     * 生成一个微信二维码，用于扫码登陆
     *
     * @param httpSession
     * @return
     */
    @GetMapping("/login")
    public String genQrConnect(HttpSession httpSession) {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = wxConfigProperties.getRedirectUrl(); //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        //存储一个state值，用来验证等会的回调连接
        redisUtils.set("wxState","guli");

        String qrCodeUrl = String.format(baseUrl,
                wxConfigProperties.getAppId(),
                redirectUrl,
                "guli");

        return "redirect:" + qrCodeUrl;

    }


    /**
     * 用于微信的回调，扫描之后会访问这个地址
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String wxCallback(@RequestParam("code") String code, @RequestParam("state") String state) {


        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        //请求地址的拼接
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                wxConfigProperties.getAppId(),
                wxConfigProperties.getAppSecret(),
                code);

        //得到 accesstoken  和 openid
        WxAccessToken forObject = restTemplate.getForObject(accessTokenUrl, WxAccessToken.class);



        return "redirect:http://localhost:3000";
    }
}
