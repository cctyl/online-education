package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacher/front")
@CrossOrigin
public class TeacherFrontController {


    @Autowired
    private EduTeacherService eduTeacherService;


    @Autowired
    private EduCourseService courseService;



    /**
     * 分页查询讲师
     *
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/{page}/{limit}")
    @ApiOperation("分页查询讲师")
    public R getTeacherFrontList(@PathVariable("page") Integer page, @PathVariable("limit") Long limit) {

        Page<EduTeacher> pageBean = new Page<>(page, limit);

        Map<String, Object> map = eduTeacherService.getTeacherFrontList(pageBean);

        return R.ok().data(map);
    }


    /**
     * 获取讲师详情信息
     * @param teacherId
     * @return
     */
    @GetMapping("/info/{teacherId}")
    @ApiOperation("前台获取讲师详情信息")
    public R getTeacherInfo(@PathVariable("teacherId") String teacherId){

        //1.查询讲师详情
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);


        //2.查询讲师讲过的课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = courseService.list(wrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",list);

    }

}
