package com.atguigu.statistics.feign;

import com.atguigu.commonutils.R;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter" ,fallback = UcenterClientFallback.class)
public interface UcenterClient {


    /**
     * 根据日期查询注册人数
     * @param day
     * @return
     */
    @GetMapping("/educenter/member/count/{day}")
    public R getDailyRegister(@ApiParam("查询日期") @PathVariable("day") String day);

    /**
     * 获取今日登陆人数
     * @return 今日登陆人数
     */
    @GetMapping("/educenter/member/count/loginNum")
    public Integer getDailyLoginNum();
}
