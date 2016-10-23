package tk.zhangh.mybatis.services;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.mybatis.domain.Student;
import tk.zhangh.mybatis.mappers.StudentMapper;

import java.util.List;

/**
 * 使用MyBatis操作Student
 *
 * Created by ZhangHao on 2016/10/20.
 */
public class StudentService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private SqlSessionFactory factory;

	public StudentService(SqlSessionFactory factory) {
		this.factory = factory;
	}
	
	protected SqlSession openSqlSession() {
		return factory.openSession();
	}

	public List<Student> findAllStudents() {
		SqlSession sqlSession = openSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			return studentMapper.findAllStudents();
		} finally {
			sqlSession.close();
		}
	}
	
	public Student findStudentById(Integer studId) {
		logger.debug("Select Student By ID :{}", studId);
		SqlSession sqlSession = openSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			return studentMapper.findStudentById(studId);
		} finally {
			sqlSession.close();
		}
	}
	
	public Student createStudent(Student student) {
		SqlSession sqlSession = openSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			studentMapper.insertStudent(student);
			sqlSession.commit();
			return student;
		} finally {
			sqlSession.close();
		}
	}
	
	public void updateStudent(Student student) {
		SqlSession sqlSession = openSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			studentMapper.updateStudent(student);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
}
