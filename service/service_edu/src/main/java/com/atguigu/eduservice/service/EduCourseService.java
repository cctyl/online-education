package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.front.CourseFrontQuery;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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


    CourseInfoVo getPublishCourseInfoById(String id);

    void getPublish(String id);


    void removeCourse(String id);

    List<EduCourse> getHotCourse();

    Map<String, Object> getCourseInfoList(Page<EduCourse> coursePage, CourseFrontQuery courseFrontQuery);

    CourseWebVo getCourseDetailById(String courseId);

    void updateViewCount(String courseId);

    Integer getDailyCourseAddition(String day);





}
