package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tyl
 * @since 2020-07-19
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getCourseChapterInfo(String courseId);
}
