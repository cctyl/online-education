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
     * 查询章节信息
     *
     * @return
     */
    @Override
    public List<ChapterVo> getCourseChapterInfo(String courseId) {

        //1.根据课程id查询所有的章节信息
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        chapterWrapper.orderByAsc("gmt_modified");
        List<EduChapter> eduChapters = baseMapper.selectList(chapterWrapper);

        //1.1转换为ChapterVo
        List<ChapterVo> finalList = new ArrayList<>();
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);


            //1.2查询这个ChapterVo下面的VideoVo
            List<EduVideo> chapter_id = eduVideoMapper.selectList(new QueryWrapper<EduVideo>().eq("chapter_id", eduChapter.getId()));

            List<VideoVo> videoVoList = copyValueToList(chapter_id, new VideoVo());

            chapterVo.setChildren(videoVoList);

            finalList.add(chapterVo);

        }


        return finalList;
    }


    /**
     * 将List<E> 转换为 List<T>
     * @param oldList
     * @param newObj
     * @return
     */
    public List<VideoVo> copyValueToList(List<EduVideo> oldList, VideoVo newObj){
        List<VideoVo> newList = new ArrayList<>();
        for (EduVideo e : oldList) {
            BeanUtils.copyProperties(e,newObj);
            newList.add(newObj);
        }

        return newList;
    }




}
