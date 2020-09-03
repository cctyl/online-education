package com.atguigu.eduservice.feign;

import com.atguigu.eduservice.entity.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "service-ucenter",fallback = UcenterClientFallback.class)
@Component
public interface UcenterClient {


    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @PostMapping("/educenter/member/info/{id}")
    public UcenterMember getUserInfoById(@PathVariable("id") String id);
}
