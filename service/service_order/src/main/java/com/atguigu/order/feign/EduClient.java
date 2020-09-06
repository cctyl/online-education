package com.atguigu.order.feign;


import com.atguigu.commonutils.R;
import com.atguigu.order.entity.vo.CourseInfoVo;
import com.atguigu.order.feign.impl.EduClientFallback;
import com.atguigu.order.feign.impl.UcenterClientFallback;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-edu",fallback = EduClientFallback.class)
@Component
public interface EduClient {

    /**
     * 查询课程信息
     * @param id
     * @return
     */
    @GetMapping("/eduservice/course/order/{id}")
    @ApiOperation("获取课程信息")
    public CourseInfoVo getCourseInfoOrder(@PathVariable("id") @ApiParam("课程ID") String id);

}
