package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.feign.VodClient;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @Autowired
    VodClient vodClient;

    @PostMapping("/")
    @ApiOperation("添加小节")
    public R saveVideo(@RequestBody @ApiParam("课程小节信息") EduVideo eduVideo) {

        eduVideoService.save(eduVideo);
        return R.ok();
    }


    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除小节")
    public R deleteVideo(@ApiParam("小节id") @PathVariable("id") String id) {

        EduVideo byId = eduVideoService.getById(id);

        if (!StringUtils.isEmpty(byId.getVideoSourceId())){
            //删除阿里云中的视频
            R r = vodClient.removeVideoById(byId.getVideoSourceId());
            if (r.getCode()==20001){

                throw new GuliException(20001,"删除视频失败，熔断器触发");
            }
        }

        //删除小节信息
        eduVideoService.removeById(id);


        return R.ok();
    }



    @PutMapping("/")
    @ApiOperation("修改小节信息")
    public R updateVideo(@RequestBody @ApiParam("课程小节信息") EduVideo eduVideo) {

        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    /**
     * 根据id获取小节信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getVideoInfoById(@ApiParam("小节id") @PathVariable("id") String id){

        EduVideo byId = eduVideoService.getById(id);

        return R.ok().data("item",byId);
    }

}

