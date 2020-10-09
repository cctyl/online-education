package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author tyl
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/eduservice/subject")

public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 添加课程
     * @return
     */
    @PostMapping("/")
    public R addSubject(MultipartFile file){


        eduSubjectService.saveSubject(file);
        return R.ok();

    }


    @GetMapping("/")
    public R getAllSubject(){

        List<OneSubject> list = eduSubjectService.getAllOrderSubject();
        return R.ok().data("list",list);
    }

}

