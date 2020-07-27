package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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






}

