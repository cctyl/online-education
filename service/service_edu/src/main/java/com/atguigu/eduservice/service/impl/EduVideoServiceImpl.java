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

    /**
     * 根据课程id删除视频
     * @param id
     */
    @Override
    public void removeVideosByCourseId(String id) {

        //1.查询出视频id，调用远程接口删除阿里云视频
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);
        List<String> ids = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            if (eduVideo.getVideoSourceId()!=null){

                ids.add(eduVideo.getVideoSourceId());
            }
        }

        if(ids.size()>0){

            vodClient.removeVideoByIdList(ids);
        }

        //2.删除 EduVideo表中的数据
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper2);
    }


    /**
     * 根据章节id删除视频
     * @param id
     */
    @Override
    public void removeVideosByChapterId(String id) {

        //1.查询出视频id，调用远程接口删除阿里云视频
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);
        List<String> ids = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            if (eduVideo!=null){

                ids.add(eduVideo.getVideoSourceId());
            }
        }

        if(ids.size()>0){

            vodClient.removeVideoByIdList(ids);
        }

        //2.删除 EduVideo表中的数据
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        baseMapper.delete(wrapper2);
    }


    /**
     * 根据云端视频资源id获取视频小节信息
     * @param id
     * @return
     */
    @Override
    public EduVideo getByVId(String id) {

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("video_source_id",id);
        EduVideo eduVideo = baseMapper.selectOne(wrapper);

        return eduVideo;
    }


}
