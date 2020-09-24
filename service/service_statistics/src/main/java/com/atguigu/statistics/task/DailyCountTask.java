package com.atguigu.statistics.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.commonutils.R;
import com.atguigu.statistics.feign.UcenterClient;
import com.atguigu.statistics.service.DailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 负责完成每天的统计任务
 */
@Component
@Slf4j
public class DailyCountTask {

    @Autowired
    private DailyService dailyService;

    @Autowired
    UcenterClient ucenterClient;

    /**
     * 定时统计日登陆数、日播放数、日注册人数、日新增课程数
     */
    public void dailyCount(){
        String today = DateUtil.formatDate(new Date());



        //TODO 统计日注册人数
        R result = ucenterClient.getDailyRegister(today);
        Integer registerCount = (Integer) result.getData().get("count");

        //TODO 调用 vod模块，拿到日播放数

        //TODO 调用ucenter模块，拿到日登陆数

        //TODO 调用eduservice模块，获取日新增的课程数
    }


}
