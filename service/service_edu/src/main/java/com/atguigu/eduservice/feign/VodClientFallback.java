package com.atguigu.eduservice.feign;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientFallback implements VodClient {

    //当vodClient对应的方法出现错误，就会执行下面的方法
    @Override
    public R removeVideoById(String id) {
        return R.error().message("删除视频出错，请稍后再试");
    }

    @Override
    public R removeVideoByIdList(List<String> videoIdList) {
        return R.error().message("删除多个视频出错，请稍后再试");
    }
}
