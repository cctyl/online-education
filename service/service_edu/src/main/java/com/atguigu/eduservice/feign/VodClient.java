package com.atguigu.eduservice.feign;

import com.atguigu.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-vod")
@Component
public interface VodClient {


    /**
     * 根据id删除阿里云中的视频
     * @param id
     * @return
     */
    @DeleteMapping("/eduvod/video/{id}")
    public R removeVideoById( @PathVariable("id") String id);
}
