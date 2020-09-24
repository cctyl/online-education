package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.commonutils.JWTUtils;
import com.atguigu.commonutils.R;
import com.atguigu.exceptionhandler.GuliException;
import com.atguigu.vod.entity.CourseInfoVo;
import com.atguigu.vod.entity.EduVideo;
import com.atguigu.vod.feign.EduClient;
import com.atguigu.vod.feign.OrderClient;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantProperties;
import com.atguigu.vod.utils.InitVodClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    VodService vodService;

    @Autowired
    ConstantProperties constantProperties;

    @Autowired
    OrderClient orderClient;

    @Autowired
    EduClient eduClient;


    @Autowired
    ObjectMapper objectMapper;

    /**
     * 将视频上传到阿里云，返回一个视频id
     *
     * @param file 文件流
     * @return 视频id
     */
    @PostMapping("/upload")
    @ApiOperation("视频上传")
    public R uploadVideo(@ApiParam("文件") MultipartFile file) {

        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);

    }

    /**
     * 根据id删除阿里云中的视频
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除视频")
    public R removeVideoById(@ApiParam("id") @PathVariable("id") String id) {

        vodService.removeVideoById(id);
        return R.ok();

    }

    /**
     * 根据id列表删除视频
     *
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/deleteBatch")
    public R removeVideoByIdList(@RequestParam("videoIdList") List<String> videoIdList) {


        vodService.removeVideoByIdList(videoIdList);
        return R.ok();

    }

    /**
     * 根据视频id获取凭证
     * TODO 根据用户是否登陆，释放购买来决定是否返回播放凭证，暂时遇到的问题是，拿不到前台传来的token
     * @param id
     * @return
     */
    @GetMapping("/playAuth/{id}")
    public R getPlayInfo(@PathVariable("id") @ApiParam("视频资源id") String id,
                         HttpServletRequest httpServletRequest
    ) {

        try {

            //1.判断这个课程是否可以试听，可以则往下走，不可以则返回错误信息
            EduVideo eduVideo = eduClient.getVideoInfoByVId(id);
            if (eduVideo==null){
                return R.error();
            }

            if (!eduVideo.getIsFree()){
                //不免费
                //判断用户是否登陆
                String memberIdByJwtToken = JWTUtils.getMemberIdByJwtToken(httpServletRequest);
                if (StringUtils.isEmpty(memberIdByJwtToken)) {

                    return R.error().message("未登陆");
                }

                //查询这个用户有没有购买这个课程
                boolean buy = orderClient.isBuy(eduVideo.getCourseId(), memberIdByJwtToken);
                if (!buy){
                    //没买，返回提示
                    return R.error().message("请购买此课程后再尝试播放");

                }
            }

            //3.验证通过，开始获取凭证
            DefaultAcsClient defaultAcsClient = InitVodClient.initVodClient(constantProperties.getKeyid(), constantProperties.getKeysecret());


            //创建获取视频地址request 和 response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            //给请求添加参数：设置id
            request.setVideoId(id);

            //执行请求后获得响应
            response = defaultAcsClient.getAcsResponse(request);
            String playAuth = response.getPlayAuth();

            vodService.addPlayNum();

            return R.ok().data("playAuth", playAuth);

        } catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败:" + ExceptionUtil.getMessage(e));
        }

    }



    //TODO 添加一个接口，从redis中拿到日播放数，返回给调用者 key是 playNums（每日通过定时任务重置key）



}
