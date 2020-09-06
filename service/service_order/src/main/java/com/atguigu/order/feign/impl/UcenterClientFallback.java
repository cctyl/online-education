package com.atguigu.order.feign.impl;

import com.atguigu.order.entity.vo.UcenterMember;
import com.atguigu.order.feign.UcenterClient;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientFallback implements UcenterClient {
    @Override
    public UcenterMember getUserInfoById(String id) {

        return null;
    }
}
