package com.atguigu.statistics.controller;


import com.atguigu.commonutils.R;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @Autowired
    private DailyService dailyService;


    /**
     * 根据开始日期和结束日期查询统计数据
     * @return
     */
    @GetMapping("/{begin}/{end}")
    public R getDailyCount(@PathVariable("begin")String begin,@PathVariable("end") String end){

        Map<String,Object> map = dailyService.getDailyCount(begin,end);
        return R.ok().data(map);
    }




}

