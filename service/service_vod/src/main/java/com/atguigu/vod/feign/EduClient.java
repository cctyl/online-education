package com.atguigu.vod.feign;

import com.atguigu.commonutils.R;
import com.atguigu.vod.entity.CourseInfoVo;
import com.atguigu.vod.entity.EduVideo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-edu", fallback = EduClientFallback.class)
@Component
public interface EduClient {




    /**
     * 用于Vod模块调用，播放前的权限判断
     * @param id
     * @return
     */
    @GetMapping("/eduservice/eduvideo/vod/{id}")
    public EduVideo getVideoInfoByVId(@ApiParam("小节id") @PathVariable("id") String id);
}
