package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2020-07-19
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    EduCourseMapper eduCourseMapper;

    /**
     * 添加课程信息
     * 向两张表添加数据
     *
     * @param courseInfoVo
     */
    @Override
    public void saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        courseInfoVo.setId(eduCourse.getId());//为了方便controller返回id 而设置
        if (insert <= 0) {
            throw new GuliException(20001, "添加课程基本信息出错");
        }

        //2.向课程简介表添加数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescription.setId(eduCourse.getId());//为了让课程记录和课程描述的id相同，eduCourseDescription的ID就必须和eduCourse一样，不能让他自动生成
        int insert2 = courseDescriptionMapper.insert(eduCourseDescription);
        if (insert2 <= 0) {
            throw new GuliException(20001, "添加课程描述信息出错");
        }
    }

    /**
     * 修改课程信息
     *
     * @param id
     */
    @Override
    public void updateCourseInfo(String id, CourseInfoVo courseInfoVo) {
        //1.实体类转换，便于后续修改
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        eduCourse.setId(id);

        //2.执行修改
        int i = baseMapper.updateById(eduCourse);
        if (i <= 0) {

            throw new GuliException(20001, "修改课程信息出错");
        }


        //3. 转换课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        int i2 = courseDescriptionMapper.updateById(eduCourseDescription);
        if (i2 <= 0) {
            throw new GuliException(20001, "修改课程简介出错");

        }


    }

    /**
     * 通过id获取课程信息
     *
     * @param id
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfoById(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        if (eduCourse != null) {

            BeanUtils.copyProperties(eduCourse, courseInfoVo);
        } else {
            throw new GuliException(20001, "查询课程信息失败，无此课程");
        }

        EduCourseDescription eduCourseDescription = courseDescriptionMapper.selectById(id);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    /**
     * 获取即将发布的课程的信息
     * @param id
     * @return
     */
    @Override
    public CourseInfoVo getPublishCourseInfoById(String id) {

        CourseInfoVo courseInfoById = eduCourseMapper.getCourseInfoById(id);
        if (courseInfoById==null){
            throw new GuliException(20001,"查询的课程不存在");
        }
        return courseInfoById;
    }

    /**
     * 发布课程，就是将status字段改为Normal
     * @param id
     */
    @Override
    public void getPublish(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        int i = baseMapper.updateById(eduCourse);
        if (i<=0){
            throw new GuliException(20001,"发布失败，没有这个课程");
        }

    }
}
