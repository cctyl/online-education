package com.atguigu.vod.feign;


import org.springframework.stereotype.Component;

@Component
public class OrderClientFallback implements OrderClient {
    @Override
    public boolean isBuy(String courseId, String memberId) {
        return false;
    }
}
