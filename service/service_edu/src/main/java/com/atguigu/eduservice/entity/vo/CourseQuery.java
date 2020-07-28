package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseQuery {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "最低价")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "最高价")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;


    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;
}
