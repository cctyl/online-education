package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2020-09-02
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    /**
     * 查询评论列表
     * @param eduCommentPage
     * @return
     */
    @Override
    public Map<String, Object> getCommentList(Page<EduComment> eduCommentPage) {

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");


        baseMapper.selectPage(eduCommentPage,wrapper);
        List<EduComment> records = eduCommentPage.getRecords();
        long current = eduCommentPage.getCurrent();
        long pages = eduCommentPage.getPages();
        long size = eduCommentPage.getSize();
        long total = eduCommentPage.getTotal();
        boolean hasNext = eduCommentPage.hasNext();
        boolean hasPrevious = eduCommentPage.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);


        return map;
        
    }
}
