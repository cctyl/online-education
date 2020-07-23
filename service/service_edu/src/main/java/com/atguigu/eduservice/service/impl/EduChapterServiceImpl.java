package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2020-07-19
 */
@Service
public class EduChapterServiceImpl<T,E> extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduVideoMapper eduVideoMapper;

    /**
     * 查询当前课程的章节信息
     * MERCURY_66B8
     * @return
     */
    @Override
    public List<ChapterVo> getCourseChapterInfo(String courseId) {

        //1.根据课程id查询所有的章节信息
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        chapterWrapper.orderByAsc("sort");
        List<EduChapter> eduChapters = baseMapper.selectList(chapterWrapper);


        //2.根据课程ID查询所有的小节信息
        List<EduVideo> eduVideoList = eduVideoMapper.selectList(new QueryWrapper<EduVideo>().eq("course_id", courseId));

        //3.1转换为ChapterVo
        List<ChapterVo> finalList = new ArrayList<>();
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            //VideoVo 集合
            List<VideoVo> childList = new ArrayList<>();

            //3.2查询这个ChapterVo下面的VideoVo
            for (EduVideo eduVideo : eduVideoList) {

                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    childList.add(videoVo);
                }
            }



            //3.3给这个ChapterVo注入 childrenList
            chapterVo.setChildren(childList);

            //3.4 转换完成
            finalList.add(chapterVo);
        }


        return finalList;
    }



}
