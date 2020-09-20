package com.atguigu.statistics.controller;


import com.atguigu.commonutils.R;
import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author tyl
 * @since 2020-09-16
 */
@RestController
@RequestMapping("/statistics/daily")
public class DailyController {

    //TODO 调用ucenter模块，拿到日登陆数

    @Autowired
    DailyService dailyService;
    /**
     * 统计日注册人数
     * @param day
     * @return
     */
    @PostMapping("regsiterCount/{day}")
    public R  countRegister(@PathVariable("day") String day){

        dailyService.countRegister(day);

        return R.ok();
    }

    //TODO 调用 vod模块，拿到日播放数

    //TODO 调用eduservice模块，获取日新增的课程数

}

