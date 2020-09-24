package com.atguigu.statistics.feign;



import com.atguigu.commonutils.R;
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
     * 从数据库中查出当日新增课程数。然后返回给调用者
     * @param day
     * @return
     */
    @GetMapping("/eduservice/course/dailyCourse/{day}")
    @ApiOperation("拿到每日新增的课程数")
    public R getDailyCourseAddition(@PathVariable("day") String day) ;

}
