package com.atguigu.order.feign.impl;

import com.atguigu.commonutils.R;
import com.atguigu.order.feign.EduClient;
import org.springframework.stereotype.Component;

@Component
public class EduClientFallback implements EduClient {


    @Override
    public R getCourseInfo(String id) {
        return null;
    }
}
