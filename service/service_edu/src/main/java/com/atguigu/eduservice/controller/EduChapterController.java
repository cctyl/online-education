package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    @GetMapping("/{id}")
    @ApiOperation("根据id获取课程章节信息")
    public R  getCourseChapter(@ApiParam("课程ID") @PathVariable("id") String courseId){

        List<ChapterVo>  chapterInfos = eduChapterService.getCourseChapterInfo(courseId);
        return R.ok().data("list",chapterInfos);
    }





}

