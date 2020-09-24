package com.atguigu.statistics.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "service-vod",fallback = VodClientFallback.class)
public interface VodClient {

    @GetMapping("/eduvod/video/count/playNum")
    public Integer getDailyPlayNum();
}
