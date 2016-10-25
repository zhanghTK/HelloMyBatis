package tk.zhangh.mybatis.mappers;

import tk.zhangh.mybatis.domain.Course;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhangHao on 2016/10/23.
 */
public interface CourseMapper {
    List<Course> selectCourseByTutor(int tutorId);

    List<Course> searchCourses(Map<String, Object> map);

    List<Course> searchCoursesByTutors(Map<String, Object> map);
}
