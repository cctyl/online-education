package com.atguigu.vod.feign;

import com.atguigu.commonutils.R;
import com.atguigu.vod.entity.CourseInfoVo;
import com.atguigu.vod.entity.EduVideo;
import org.springframework.stereotype.Component;

@Component
public class EduClientFallback implements EduClient {

    @Override
    public EduVideo getVideoInfoByVId(String id) {
        return null;
    }
}
