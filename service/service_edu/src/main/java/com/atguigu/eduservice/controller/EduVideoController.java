package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author tyl
 * @since 2020-07-19
 */
@RestController
@RequestMapping("/eduservice/eduvideo")
@CrossOrigin
public class EduVideoController {


    @Autowired
    EduVideoService eduVideoService;

    @PostMapping("/")
    @ApiOperation("添加小节")
    public R saveVideo(@RequestBody @ApiParam("课程小节信息") EduVideo eduVideo) {

        eduVideoService.save(eduVideo);
        return R.ok();
    }


    /**
     * TODO 后续要把小节后面下面的视频也一起删掉
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除小节")
    public R deleteVideo(@ApiParam("课程小节信息") @PathVariable("id") String id) {
        eduVideoService.removeById(id);
        return R.ok();
    }



    @PutMapping("/")
    @ApiOperation("修改小节信息")
    public R updateVideo(@RequestBody @ApiParam("课程小节信息") EduVideo eduVideo) {

        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

}

