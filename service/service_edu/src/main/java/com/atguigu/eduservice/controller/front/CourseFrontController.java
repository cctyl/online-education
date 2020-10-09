package com.atguigu.eduservice.controller.front;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.commonutils.JWTUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.vo.front.CourseFrontQuery;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
import com.atguigu.eduservice.feign.OrderClient;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/course/front")

public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    /**
     * 分页条件查询课程列表
     *
     * @return
     */
    @PostMapping("/{page}/{limit}")
    @ApiOperation("分页条件查询课程列表")
    public R getCourseInfoList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable("page") Integer page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseFrontQuery courseFrontQuery
    ) {

        Page<EduCourse> coursePage = new Page<>();

        Map<String,Object> map = eduCourseService.getCourseInfoList(coursePage,courseFrontQuery);

        return R.ok().data(map);
    }


    /**
     * 查询课程详情信息
     * @param courseId
     * @return 课程信息以及课程下面的章节和小节
     */
    @PostMapping("/detail/{courseId}")
    @ApiOperation("查询课程详情信息")
    public R getCourseDetail(@ApiParam("课程id") @PathVariable("courseId") String courseId,
                             HttpServletRequest httpServletRequest){
        //查询课程主体信息
        CourseWebVo course = eduCourseService.getCourseDetailById(courseId);

        //查询课程购买情况
        String memberIdByJwtToken = JWTUtils.getMemberIdByJwtToken(httpServletRequest);

        if (StringUtils.isEmpty(memberIdByJwtToken)){
            //如果为空，就是没登陆，购买状态设置为false
            course.setBuyStatus(false);
        }else {
            //不为空，调用order模块的接口，查询用互是否购买课程
            course.setBuyStatus(orderClient.isBuy(courseId, memberIdByJwtToken));
        }
        //查询课程下面的章节和小节信息
        List<ChapterVo> courseChapterInfo = eduChapterService.getCourseChapterInfo(courseId);


        return R.ok().data("course",course).data("chapterVoList",courseChapterInfo);
    }


}
