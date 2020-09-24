package com.atguigu.statistics.task;

import cn.hutool.core.date.DateUtil;
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

    /**
     * 定时统计日登陆数、日播放数、日注册人数、日新增课程数
     */
    public void dailyCount(){
        String today = DateUtil.formatDate(new Date());
        //1.统计日注册人数
        dailyService.countRegister(today);


    }


}
