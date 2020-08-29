package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.entity.vo.front.CourseFrontQuery;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    EduVideoService eduVideoService;


    @Autowired
    EduChapterService eduChapterService;

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
     *
     * @param id
     * @return
     */
    @Override
    public CourseInfoVo getPublishCourseInfoById(String id) {

        CourseInfoVo courseInfoById = eduCourseMapper.getCourseInfoById(id);
        if (courseInfoById == null) {
            throw new GuliException(20001, "查询的课程不存在");
        }
        return courseInfoById;
    }

    /**
     * 发布课程，就是将status字段改为Normal
     *
     * @param id
     */
    @Override
    public void getPublish(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        int i = baseMapper.updateById(eduCourse);
        if (i <= 0) {
            throw new GuliException(20001, "发布失败，没有这个课程");
        }

    }

    /**
     * 根据课程id删除课程以及下面的课程信息
     *
     * @param id
     * @return
     */
    @Override
    public void removeCourse(String id) {
        //删除小节
        eduVideoService.removeVideosByCourseId(id);
        //删除章节
        eduChapterService.removeByCourseId(id);
        //删除课程
        eduCourseMapper.deleteById(id);

    }

    /**
     * 获取热门课程信息
     *
     * @return
     */
    @Override
    @Cacheable(value = "course", key = "'selectIndexList'")
    public List<EduCourse> getHotCourse() {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> courseList = this.list(wrapper);
        return courseList;
    }


    /**
     * 分页条件查询课程列表
     *
     * @return
     */
    @Override
    public Map<String, Object> getCourseInfoList(Page<EduCourse> coursePage, CourseFrontQuery courseFrontQuery) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        //一级分类
        if (!StringUtils.isEmpty(courseFrontQuery.getSubjectParentId())) {

            wrapper.eq("subject_parent_id", courseFrontQuery.getSubjectParentId());
        }

        //二级分类
        if (!StringUtils.isEmpty(courseFrontQuery.getSubjectId())) {

            wrapper.eq("subject_id", courseFrontQuery.getSubjectId());
        }

        //关注度排序
        if (!StringUtils.isEmpty(courseFrontQuery.getBuyCountSort())) {

            switch (courseFrontQuery.getBuyCountSort()) {
                case "1":
                    wrapper.orderByAsc("buy_count");
                    break;

                case "2":
                    wrapper.orderByDesc("buy_count");

            }
        }

        //最新的课程
        if (!StringUtils.isEmpty(courseFrontQuery.getGmtCreateSort())) {

            switch (courseFrontQuery.getGmtCreateSort()) {
                case "1":
                    wrapper.orderByAsc("gmt_create");
                    break;

                case "2":
                    wrapper.orderByDesc("gmt_create");

            }
        }


        //价格排序
        if (!StringUtils.isEmpty(courseFrontQuery.getPriceSort())) {

            switch (courseFrontQuery.getPriceSort()) {
                case "1":
                    wrapper.orderByAsc("price");
                    break;

                case "2":
                    wrapper.orderByDesc("price");

            }
        }


        baseMapper.selectPage(coursePage, wrapper);

        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }


    /**
     * 查询课程信息以及附带的讲师数据，还有分类数据
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getCourseDetailById(String courseId) {

        this.updateViewCount(courseId);
        return baseMapper.getCourseDetail(courseId);

    }

    @Override
    public void updateViewCount(String courseId) {

        EduCourse eduCourse = baseMapper.selectById(courseId);
        eduCourse.setViewCount(eduCourse.getViewCount() + 1);
        baseMapper.updateById(eduCourse);
    }


}
