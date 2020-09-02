package com.atguigu.eduservice.feign;

import com.atguigu.eduservice.entity.vo.UcenterMember;
import com.atguigu.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientFallback implements UcenterClient {
    @Override
    public UcenterMember getUserInfoById(String id) {
       throw new GuliException(20001,"获取用户信息失败");
    }
}
