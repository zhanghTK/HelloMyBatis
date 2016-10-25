package tk.zhangh.mybatis.mappers;

import tk.zhangh.mybatis.domain.Tutor;

/**
 * Created by ZhangHao on 2016/10/23.
 */
public interface TutorMapper {
    Tutor selectTutorWithCourses(int tutorId);

    Tutor selectTutorById(int tutorId);
}
