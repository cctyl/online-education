package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tyl
 * @since 2020-07-19
 */
public interface EduCourseService extends IService<EduCourse> {

    void saveCourseInfo(CourseInfoVo courseInfoVo);

    void updateCourseInfo(String id,CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoById(String id);
}
