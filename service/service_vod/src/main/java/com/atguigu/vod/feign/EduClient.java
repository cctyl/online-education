package com.atguigu.vod.feign;

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

    @GetMapping("/eduservice/course/order/{id}")
    @ApiOperation("获取课程信息")
    public CourseInfoVo getCourseInfoOrder(@PathVariable("id") @ApiParam("课程ID") String id);


}
