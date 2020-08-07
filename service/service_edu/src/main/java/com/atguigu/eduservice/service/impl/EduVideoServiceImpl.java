package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.feign.VodClient;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2020-07-19
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;

    @Override
    public void removeVideosByCourseId(String id) {

        //1.查询出视频id，调用远程接口删除阿里云视频
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);
        List<String> ids = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {

            ids.add(eduVideo.getVideoSourceId());
        }
        vodClient.removeVideoByIdList(ids);

        //2.删除 EduVideo表中的数据
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper2);
    }
}
