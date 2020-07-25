package com.atguigu.eduservice.entity.chapter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterVo {

    private String id;


    private String title;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    List<VideoVo> children = new ArrayList<>();
}
