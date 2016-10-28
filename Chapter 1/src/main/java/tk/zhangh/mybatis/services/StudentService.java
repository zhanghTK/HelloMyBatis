package tk.zhangh.mybatis.services;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.mybatis.domain.Student;
import tk.zhangh.mybatis.mappers.StudentMapper;
import tk.zhangh.mybatis.util.MyBatisSqlSessionFactory;

import java.util.List;

/**
 * 使用MyBatis操作Student
 * Created by ZhangHao on 2016/10/18.
 */
public class StudentService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<Student> findAllStudents() {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.findAllStudents();
        } finally {
            sqlSession.close();
        }
    }

    public Student findStudentById(Integer studId) {
        logger.debug("Select Student By ID :{}", studId);
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.findStudentById(studId);
            //return sqlSession.selectOne("tk.zhangh.mybatis.StudentMapper.findStudentById", studId);
        } finally {
            sqlSession.close();
        }
    }

    public void createStudent(Student student) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            studentMapper.insertStudent(student);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public void updateStudent(Student student) {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            studentMapper.updateStudent(student);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
