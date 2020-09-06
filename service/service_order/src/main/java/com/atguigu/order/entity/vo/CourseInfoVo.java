package com.atguigu.order.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;


    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "课程讲师名称")
    private String teacherName;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;



    @ApiModelProperty(value = "课程专业名称")
    private String subjectName;


    @ApiModelProperty(value = "课程专业父级名称")
    private String subjectParentName;



    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;


    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;




}
