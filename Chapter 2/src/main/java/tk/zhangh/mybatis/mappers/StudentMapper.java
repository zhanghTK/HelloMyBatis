package tk.zhangh.mybatis.mappers;

import tk.zhangh.mybatis.domain.Student;

import java.util.List;

/**
 * Student映射器接口
 *
 * Created by ZhangHao on 2016/10/20.
 */
public interface StudentMapper {
    List<Student> findAllStudents();

    Student findStudentById(Integer id);

    void insertStudent(Student student);

    void updateStudent(Student student);
}
