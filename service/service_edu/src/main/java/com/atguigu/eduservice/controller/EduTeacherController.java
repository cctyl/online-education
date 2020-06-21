package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author tyl
 * @since 2020-06-19
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    EduTeacherService eduTeacherService;

    @ApiOperation("获取所有讲师列表")
    @GetMapping("/list")
    public R findAllTeacher() {


        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation("删除一个讲师")
    @DeleteMapping("/{id}")
    public R deleteTeacher(
            @ApiParam("讲师的ID")
            @PathVariable("id") String id) {


        boolean b = eduTeacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    @ApiOperation("讲师分页查询")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam("当前页") @PathVariable("current") Integer current,
            @ApiParam("每页显示的记录数") @PathVariable("limit") Integer limit) {


        Page<EduTeacher> page = new Page<>(current, limit);
        //service的分页查询方法，第一个参数是 封装的page，第二个参数 是查询条件。查询的结果会直接放到page对象中
        eduTeacherService.page(page, null);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        Map<String, Object> map = new HashedMap<>();
        map.put("total", total);
        map.put("rows", records);

        return R.ok().data(map);
    }

    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    @ApiOperation("讲师组合条件查询（分页）")
    public R pageListByCondition(@ApiParam("当前页") @PathVariable("current") Integer current,
                                 @ApiParam("每页显示的记录数") @PathVariable("limit") Integer limit,
                                 @ApiParam("组合查询条件") @RequestBody(required = false) TeacherQuery teacherQuery
    ) {

        //创建page对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);

        //创建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多个条件查询，但是有些条件可能没有，要怎么解决呢？用if一个个判断
        //动态sql技术
        //判断是否为空，不为空就拼接
        if (!StringUtils.isEmpty(teacherQuery.getBegin())){

            wrapper.gt("gmt_create",teacherQuery.getBegin());
        }
        if (!StringUtils.isEmpty(teacherQuery.getName())){

            wrapper.like("name",teacherQuery.getName());
        }

        if (!StringUtils.isEmpty(teacherQuery.getLevel())){
            wrapper.eq("level",teacherQuery.getLevel());
        }
        if (!StringUtils.isEmpty(teacherQuery.getEnd())){

            wrapper.lt("gmt_modified",teacherQuery.getEnd());
        }


        eduTeacherService.page(eduTeacherPage, wrapper);
        List<EduTeacher> records = eduTeacherPage.getRecords();
        long total = eduTeacherPage.getTotal();

        return R.ok().data("total",total).data("items",records);
    }

}

