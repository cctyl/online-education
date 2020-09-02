package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author tyl
 * @since 2020-09-02
 */
@RestController
@RequestMapping("/eduservice/comment/front")
@CrossOrigin
public class CommentFrontController {

    @Autowired
    EduCommentService eduCommentService;

    /**
     * 分页查询评论
     *
     * @param page  当前页码
     * @param limit 每页记录数
     * @return
     */
    @PostMapping("/{page}/{limit}")
    @ApiOperation("分页查询评论")
    public R getCommentList(@ApiParam(name = "page", value = "当前页码", required = true)
                            @PathVariable("page") Integer page,

                            @ApiParam(name = "limit", value = "每页记录数", required = true)
                            @PathVariable("limit") Long limit) {

        Page<EduComment> eduCommentPage = new Page<>(page,limit);

      Map<String,Object> map =  eduCommentService.getCommentList(eduCommentPage);

      return R.ok().data(map);
    }


}
