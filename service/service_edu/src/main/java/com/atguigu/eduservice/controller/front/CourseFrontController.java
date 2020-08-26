package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.front.CourseFrontQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/course/front")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 分页条件查询课程列表
     *
     * @return
     */
    @PostMapping("/{page}/{limit}")
    @ApiOperation("分页条件查询课程列表")
    public R getCourseInfoList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable("page") Integer page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseFrontQuery courseFrontQuery
    ) {

        Page<EduCourse> coursePage = new Page<>();

        Map<String,Object> map = eduCourseService.getCourseInfoList(coursePage,courseFrontQuery);

        return R.ok().data(map);
    }
}
