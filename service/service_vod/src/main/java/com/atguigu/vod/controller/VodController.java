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
import com.atguigu.vod.feign.EduClient;
import com.atguigu.vod.feign.OrderClient;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantProperties;
import com.atguigu.vod.utils.InitVodClient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     *
     * @param id
     * @return
     */
    @GetMapping("/playAuth/{id}/{courseId}")
    public R getPlayInfo(@PathVariable("id") @ApiParam("视频资源id") String id,
                         HttpServletRequest httpServletRequest,
                         @PathVariable("courseId") @ApiParam("课程id") String courseId) {


        try {
            //0.不登陆不允许观看
            String memberIdByJwtToken = JWTUtils.getMemberIdByJwtToken(httpServletRequest);
            if (memberIdByJwtToken == null) {

                return R.error().message("未登陆");
            }

            //1.判断这是否是收费课程，根据视频id获取
            CourseInfoVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
            boolean isFree = courseInfoOrder.getPrice().intValue() > 0 ? false : true;//false收费，true免费

            if (!isFree){
                //收费

                //2.判断用户是否购买此课程
                boolean isBuy = orderClient.isBuy(courseId, memberIdByJwtToken);
                if (!isBuy){
                    //未购买
                    return R.error().message("未购买");
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
            return R.ok().data("playAuth", playAuth);

        } catch (ClientException e) {
            throw new GuliException(20001, "获取凭证失败:" + ExceptionUtil.getMessage(e));
        }

    }


}
