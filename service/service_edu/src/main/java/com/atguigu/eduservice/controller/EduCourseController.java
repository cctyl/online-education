package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javafx.collections.transformation.SortedList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author tyl
 * @since 2020-07-19
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;

    @PostMapping("/")
    @ApiOperation("添加课程信息")
    public R saveCourse(@ApiParam("课程信息封装类") @RequestBody CourseInfoVo courseInfoVo) {

        eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("id", courseInfoVo.getId());
    }


    /**
     * 修改课程信息
     *
     * @param id           课程id
     * @param courseInfoVo
     * @return
     */
    @PutMapping("/{id}")
    @ApiOperation("修改课程信息")
    public R updateCourseInfo(@ApiParam("课程ID") @PathVariable("id") String id,
                              @ApiParam("课程信息封装类") @RequestBody CourseInfoVo courseInfoVo
    ) {


        eduCourseService.updateCourseInfo(id, courseInfoVo);
        return R.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取课程信息")
    public R getCourseInfo(@PathVariable("id") @ApiParam("课程ID") String id) {

        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoById(id);
        return R.ok().data("courseInfo", courseInfoVo);
    }


    @GetMapping("/publish/{id}")
    @ApiOperation("获取课程信息")
    public R getPublishCourseInfo(@PathVariable("id") @ApiParam("课程ID") String id) {

        CourseInfoVo courseInfoVo = eduCourseService.getPublishCourseInfoById(id);
        return R.ok().data("courseInfo", courseInfoVo);
    }


    @PutMapping("/publish/{id}")
    @ApiOperation("发布课程")
    public R publishCourse(@PathVariable("id") @ApiParam("课程ID") String id) {

        eduCourseService.getPublish(id);
        return R.ok();
    }


    @PostMapping("/pageCourseCondition/{current}/{limit}")
    @ApiOperation("课程组合条件查询")
    public R pageListByCondition(@ApiParam("当前页") @PathVariable("current") Integer current,
                                 @ApiParam("每页显示的记录数") @PathVariable("limit") Integer limit,
                                 @ApiParam("组合查询条件") @RequestBody(required = false) CourseQuery courseQuery
    ) {

        Page<EduCourse> eduCoursePage = new Page<>(current, limit);


        //创建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();


        //判断是否为空，不为空就拼接
        if (!StringUtils.isEmpty(courseQuery.getTitle())) {
            wrapper.like("title", courseQuery.getTitle());

        }

        if (!StringUtils.isEmpty(courseQuery.getTeacherId())) {
            wrapper.eq("teacher_id", courseQuery.getTeacherId());


        }

        if (courseQuery.getMaxPrice() != null) {
            wrapper.le("price", courseQuery.getMaxPrice());

        }
        if (courseQuery.getMinPrice() != null) {

            wrapper.ge("price", courseQuery.getMinPrice());
        }


        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            wrapper.eq("subject_id", courseQuery.getSubjectId());

        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());

        }

        wrapper.orderByDesc("gmt_modified");

        eduCourseService.page(eduCoursePage, wrapper);
        List<EduCourse> records = eduCoursePage.getRecords();

        //复杂查询暂时无法满足，用笨办法先查出来
        List<CourseInfoVo> courseInfoVoList = new ArrayList<>();

        for (EduCourse eduCourse : records) {

            courseInfoVoList.add(eduCourseService.getPublishCourseInfoById(eduCourse.getId()));
        }


        long total = eduCoursePage.getTotal();
        return R.ok().data("total", total).data("items", courseInfoVoList);


    }



    @DeleteMapping("/{id}")
    @ApiOperation("删除课程")
    public R deleteCourse(@PathVariable("id") @ApiParam("课程ID") String id) {


        boolean b = eduCourseService.removeById(id);
        if (b){
            return R.ok();

        }else {
            return R.error();
        }

    }
}

