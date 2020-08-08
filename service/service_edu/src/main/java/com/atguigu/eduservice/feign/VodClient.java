package com.atguigu.eduservice.feign;

import com.atguigu.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodClientFallback.class)
@Component
public interface VodClient {


    /**
     * 根据id删除阿里云中的视频
     * @param id
     * @return
     */
    @DeleteMapping("/eduvod/video/{id}")
    public R removeVideoById( @PathVariable("id") String id);


    /**
     * 根据id列表删除视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/eduvod/video/deleteBatch")
    public R removeVideoByIdList(@RequestParam("videoIdList") List<String> videoIdList);
}
