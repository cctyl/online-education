package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
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


    @Select(" SELECT" +
            "    c.id," +
            "    c.title," +
            "    c.cover," +
            "    CONVERT(c.price, DECIMAL(8,2)) AS price," +
            "    c.lesson_num AS lessonNum," +
            "    c.cover," +
            "    c.buy_count AS buyCount," +
            "    c.view_count AS viewCount," +
            "    cd.description," +

            "    t.id AS teacherId," +
            "    t.name AS teacherName," +
            "    t.intro," +
            "    t.avatar," +

            "    s1.id AS subjectLevelOneId," +
            "    s1.title AS subjectLevelOne," +
            "    s2.id AS subjectLevelTwoId," +
            "    s2.title AS subjectLevelTwo" +

            "  FROM" +
            "    edu_course c" +
            "    LEFT JOIN edu_course_description cd ON c.id = cd.id" +
            "    LEFT JOIN edu_teacher t ON c.teacher_id = t.id" +
            "    LEFT JOIN edu_subject s1 ON c.subject_parent_id = s1.id" +
            "    LEFT JOIN edu_subject s2 ON c.subject_id = s2.id" +
            "  WHERE" +
            "    c.id = #{courseId}")
    CourseWebVo getCourseDetail(String courseId);
}
