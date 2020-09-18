package com.atguigu.vod.feign;

import com.atguigu.commonutils.R;
import com.atguigu.vod.entity.CourseInfoVo;
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
     * 根据视频id获取视频小节信息
     * @param id
     * @return
     */
    @GetMapping("/eduservice/eduvideo/{id}")
    public R getVideoInfoById(@ApiParam("小节id") @PathVariable("id") String id);
}
