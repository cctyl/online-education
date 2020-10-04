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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> getDailyCount(String begin, String end) {

        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", "register_num", "login_num", "video_view_num", "course_num");

        //查出想要的列
        List<Daily> dailyList = baseMapper.selectList(wrapper);

        //每一列单独封装成一个list
       /*
       List<String> dateList = new ArrayList<>();//日期数组
        List<Integer> registerList = new ArrayList<>(); //注册人数
        List<Integer> loginList = new ArrayList<>();//登陆人数
        List<Integer> viewList = new ArrayList<>();//播放次数
        List<Integer> courseList = new ArrayList<>();//新增课程数

        for (Daily temp :
                dailyList) {

            dateList.add(temp.getDateCalculated());
            registerList.add(temp.getRegisterNum());
            loginList.add(temp.getLoginNum());
            viewList.add(temp.getVideoViewNum());
            courseList.add(temp.getCourseNum());

        }
*/
       //改用Stream流的方式
        List<String> dateList = dailyList.stream().map(Daily::getDateCalculated).collect(Collectors.toList());
        List<Integer> registerList = dailyList.stream().map(Daily::getRegisterNum).collect(Collectors.toList());
        List<Integer> loginList = dailyList.stream().map(Daily::getLoginNum).collect(Collectors.toList());
        List<Integer> viewList = dailyList.stream().map(Daily::getVideoViewNum).collect(Collectors.toList());
        List<Integer> courseList = dailyList.stream().map(Daily::getCourseNum).collect(Collectors.toList());


        Map<String, Object> map = new HashMap<>();

        map.put("dateList", dateList);
        map.put("registerList", registerList);
        map.put("loginList", loginList);
        map.put("viewList", viewList);
        map.put("courseList", courseList);

        return map;
    }


}
