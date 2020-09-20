package com.atguigu.statistics.feign;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientFallback implements UcenterClient {
    @Override
    public R getDailyRegister(String day) {
        return null;
    }
}
