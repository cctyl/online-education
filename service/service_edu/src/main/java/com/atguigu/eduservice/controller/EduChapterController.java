package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
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

public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    @GetMapping("/{id}")
    @ApiOperation("根据id获取课程章节信息")
    public R getCourseChapter(@ApiParam("课程ID") @PathVariable("id") String courseId) {

        List<ChapterVo> chapterInfos = eduChapterService.getCourseChapterInfo(courseId);
        return R.ok().data("list", chapterInfos);
    }


    @PostMapping("/")
    @ApiOperation("添加章节信息")
    public R saveChapterInfo(@RequestBody @ApiParam("章节信息") EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }



    @PutMapping("/")
    @ApiOperation("修改章节信息")
    public R updateChapterInfo(@RequestBody @ApiParam("章节信息") EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除章节信息")
    public R deleteChapter(@PathVariable("id") @ApiParam("章节信息") String id) {
        eduChapterService.removeChapterById(id);
        return R.ok();
    }

}

