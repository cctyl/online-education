package com.atguigu.eduservice.entity.vo.front;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CourseWebVo implements Serializable {

    private static final long serialVersionUID = 1L;



    private String id;

    //查询 edu_course 表获得
    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程类别ID")
    private String subjectLevelOneId;

    @ApiModelProperty(value = "课程类别ID")
    private String subjectLevelTwoId;



    //根据subjectid查询 edu_subject 表获得
    @ApiModelProperty(value = "类别名称")
    private String subjectLevelOne;

    @ApiModelProperty(value = "类别名称")
    private String subjectLevelTwo;


    @ApiModelProperty(value = "是否购买")
    private boolean buyStatus;


    //查询edu_course_description表获得
    @ApiModelProperty(value = "课程简介")
    private String description;



    //查询edu_teacher获得
    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String intro;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;





}