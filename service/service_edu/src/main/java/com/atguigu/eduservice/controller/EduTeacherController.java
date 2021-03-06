package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j

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


    @PostMapping("/")
    @ApiOperation("新增讲师")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {

        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("/{id}")
    public R findTeacherById(@PathVariable("id") String id) {

        if (StringUtils.isEmpty(id)) {

            throw new GuliException(400, "输入有误");
        }
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("item", byId);


    }


    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {

        teacher.setId(id);
        eduTeacherService.updateById(teacher);

        return R.ok();
    }


    @GetMapping("/exception/{id}")
    @ApiOperation("异常测试方法")
    public R getException(@PathVariable("id") Long id){

        if (id>10){

            throw  new GuliException(400,"id太大了");
        }
        if (id<0){
            throw  new GuliException(400,"id太小了");
        }

        try {
            int a = 10 / 0;
        } catch (Exception e) {
            throw new GuliException(400,"id不能为0");
        }

        return R.ok().message("id刚刚好");
    }

}

