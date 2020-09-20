package com.atguigu.statistics.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.feign.UcenterClient;
import com.atguigu.statistics.mapper.DailyMapper;
import com.atguigu.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2020-09-16
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {



    @Autowired
    UcenterClient ucenterClient;

    /**
     * 调用远程接口，统计日注册人数
     * @param day
     */
    @Override
    public void countRegister(String day) {
        R result = ucenterClient.getDailyRegister(day);
        Integer count = (Integer) result.getData().get("count");

        Daily daily = new Daily();
        daily.setRegisterNum(count);
        daily.setDateCalculated(day);
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));
        daily.setLoginNum(RandomUtils.nextInt(100,200));
        daily.setCourseNum(RandomUtils.nextInt(100,200));


        baseMapper.insert(daily);


    }
}
