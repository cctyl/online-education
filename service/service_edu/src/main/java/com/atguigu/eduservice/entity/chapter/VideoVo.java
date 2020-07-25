package com.atguigu.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {
    private String id;
    private String title;
    @ApiModelProperty(value = "显示排序")
    private Integer sort;
}
