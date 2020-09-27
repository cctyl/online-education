package com.atguigu.statistics.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.feign.UcenterClient;
import com.atguigu.statistics.mapper.DailyMapper;
import com.atguigu.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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





    @Override
    public void dailyCount(Daily daily) {
        baseMapper.insert(daily);
    }

    /**
     * 根据日期查询统计数据
     * @param day
     * @return
     */
    @Override
    public Daily getDailyCountByDate(String day) {

        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        Daily daily = baseMapper.selectOne(wrapper);
        return daily;
    }


}
