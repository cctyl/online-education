package com.atguigu.vod.feign;

import com.atguigu.vod.entity.CourseInfoVo;
import org.springframework.stereotype.Component;

@Component
public class EduClientFallback implements EduClient {
    @Override
    public CourseInfoVo getCourseInfoOrder(String id) {
        return null;
    }
}
