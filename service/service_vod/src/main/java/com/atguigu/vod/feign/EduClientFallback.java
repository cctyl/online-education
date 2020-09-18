package com.atguigu.vod.feign;

import com.atguigu.commonutils.R;
import com.atguigu.vod.entity.CourseInfoVo;
import org.springframework.stereotype.Component;

@Component
public class EduClientFallback implements EduClient {

    @Override
    public R getVideoInfoById(String id) {
        return R.error();
    }
}
