package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Teacher查询对象",description = "用于封装组合查询的条件")
public class TeacherQuery {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty("教师级别，1高级 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "讲师入驻时间-开始时间",example = "2019-01-01 10:10:10")
    private String begin;


    @ApiModelProperty(value = "讲师入驻时间-结束时间",example = "2019-01-01 10:10:10")
    private String end;
}
