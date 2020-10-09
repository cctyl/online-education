package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("处理教师登陆的类")
@RequestMapping("/eduservice/user")
 //解决跨域问题
public class EduLoginController {

    @PostMapping("/login")
    @ApiOperation("用于处理登录请求")
    public R login() {
        //暂时先这样，让前端通过，后期做springsecurity
        return R.ok().data("token","admin");
    }


    @GetMapping("/info")
    @ApiOperation("/info")
    public R getInfo(){
        //临时制作
        return R.ok().data("roles","[admin]")
                .data("name","管理员")
                .data("avatar","https://mmbiz.qpic.cn/mmbiz_gif/1ttiaQznP1MsuEwPP1040Ygqv4nm2RSTPN6g2EKictZSPS5QD3Btj52WNeJziciaKia9GNCz2YQEoYKVPT7lwKjYFNg/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1");
    }
}
