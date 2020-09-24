package com.atguigu.statistics.service;

import com.atguigu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author tyl
 * @since 2020-09-16
 */
public interface DailyService extends IService<Daily> {

    void dailyCount(Daily daily);


}
