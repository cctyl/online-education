package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 负责提供前台页面部分数据
 */
@RestController
@RequestMapping("/eduservice/indexfront")

public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;


    /**
     * 查询前8条热门课程，查询前4条名师
     *
     * @return
     */
    @GetMapping("/index")
    @ApiOperation("查询前台的热门课程和热门讲师数据")
    public R getIndexData() {

        //1.查询热门课程
        List<EduCourse> courseList =  eduCourseService.getHotCourse();


        //2.查询热门讲师
        List<EduTeacher> teacherList  = eduTeacherService.getHotTeacher();


        return R.ok().data("courseList", courseList).data("teacherList", teacherList);

    }


}