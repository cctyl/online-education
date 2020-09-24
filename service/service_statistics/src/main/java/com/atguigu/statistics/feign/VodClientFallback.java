package com.atguigu.statistics.feign;

public class VodClientFallback implements  VodClient {
    @Override
    public Integer getDailyPlayNum() {
        return 0;
    }
}
