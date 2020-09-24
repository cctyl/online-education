package com.atguigu.statistics.feign;

import org.springframework.stereotype.Component;

@Component
public class VodClientFallback implements  VodClient {
    @Override
    public Integer getDailyPlayNum() {
        return 0;
    }
}
