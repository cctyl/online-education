package com.atguigu.statistics.controller;


import com.atguigu.commonutils.R;
import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 根据日期查询统计数据
     * @param day
     * @return
     */
    @GetMapping("/{day}")
    public R getDailyCount(@PathVariable("day") String day){


    }



}

