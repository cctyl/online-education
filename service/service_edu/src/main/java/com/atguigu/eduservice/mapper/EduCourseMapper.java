package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author tyl
 * @since 2020-07-19
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    @Select(
            "SELECT ec.`id`,ec.`title`,ec.`price`,ec.`cover`,ec.`lesson_num`,ecd.`description`,et.`name` AS teacherName,es1.`title` AS subjectName,es2.`title` AS subjectParentName FROM edu_course ec LEFT OUTER JOIN edu_course_description ecd ON ec.id=ecd.id " +
            "  LEFT OUTER JOIN edu_teacher et ON ec.`teacher_id`=et.`id`" +
            "   LEFT OUTER JOIN edu_subject es1 ON ec.`subject_parent_id` = es1.`id`" +
            "   LEFT OUTER JOIN edu_subject es2 ON ec.`subject_id` = es2.`id`" +
            "   WHERE ec.id=#{id} and ec.is_deleted=0")
    public CourseInfoVo getCourseInfoById(String id);

}
