package com.atguigu.vod.feign;

import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-order", fallback = OrderClientFallback.class)
@Component
public interface OrderClient {

    /**
     * 查询用户是否购买此课程
     *
     * @param courseId
     * @param memberId
     * @return
     */
    @GetMapping("/orderservice/order/buystatus/{courseId}/{memberId}")
    public boolean isBuy(@ApiParam("课程id") @PathVariable("courseId") String courseId,
                         @PathVariable("memberId") String memberId);


}
