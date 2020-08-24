package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacher/front")
@CrossOrigin
public class TeacherFrontController {


    @Autowired
    private EduTeacherService eduTeacherService;

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

}
