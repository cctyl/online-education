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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public R saveCourse(@ApiParam("课程信息封装类") @RequestBody CourseInfoVo courseInfoVo){

        eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("id",courseInfoVo.getId());
    }


    /**
     * 修改课程信息
     * @param id 课程id
     * @param courseInfoVo
     * @return
     */
    @PutMapping("/{id}")
    @ApiOperation("修改课程信息")
    public R updateCourseInfo(@ApiParam("课程ID") @PathVariable("id") String id,
                              @ApiParam("课程信息封装类") @RequestBody  CourseInfoVo courseInfoVo
                              ){


        eduCourseService.updateCourseInfo(id,courseInfoVo);
        return R.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取课程信息")
    public R getCourseInfo(@PathVariable("id") @ApiParam("课程ID") String id){

        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoById(id);
        return R.ok().data("courseInfo",courseInfoVo);
    }


    @GetMapping("/publish/{id}")
    @ApiOperation("获取课程信息")
    public R getPublishCourseInfo(@PathVariable("id") @ApiParam("课程ID") String id){

        CourseInfoVo courseInfoVo = eduCourseService.getPublishCourseInfoById(id);
        return R.ok().data("courseInfo",courseInfoVo);
    }


    @PutMapping("/publish/{id}")
    @ApiOperation("发布课程")
    public R publishCourse(@PathVariable("id") @ApiParam("课程ID") String id){

        eduCourseService.getPublish(id);
        return R.ok();
    }



    @PostMapping("/pageCourseCondition/{current}/{limit}")
    @ApiOperation("课程组合条件查询")
    public R pageListByCondition(@ApiParam("当前页") @PathVariable("current") Integer current,
                                 @ApiParam("每页显示的记录数") @PathVariable("limit") Integer limit,
                                 @ApiParam("组合查询条件") @RequestBody(required = false)CourseQuery courseQuery
                                 ) {

        //创建page对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);

        //创建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多个条件查询，但是有些条件可能没有，要怎么解决呢？用if一个个判断
        //动态sql技术
        //判断是否为空，不为空就拼接
        if (!StringUtils.isEmpty(teacherQuery.getBegin())) {

            wrapper.gt("gmt_create", teacherQuery.getBegin());
        }
        if (!StringUtils.isEmpty(teacherQuery.getName())) {

            wrapper.like("name", teacherQuery.getName());
        }

        if (!StringUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level", teacherQuery.getLevel());
        }
        if (!StringUtils.isEmpty(teacherQuery.getEnd())) {

            wrapper.lt("gmt_modified", teacherQuery.getEnd());
        }

        wrapper.orderByDesc("gmt_modified");
        eduTeacherService.page(eduTeacherPage, wrapper);
        List<EduTeacher> records = eduTeacherPage.getRecords();
        long total = eduTeacherPage.getTotal();

        return R.ok().data("total", total).data("items", records);
    }





}

