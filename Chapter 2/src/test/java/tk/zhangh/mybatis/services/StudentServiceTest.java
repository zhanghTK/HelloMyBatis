package tk.zhangh.mybatis.services;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.mybatis.domain.PhoneNumber;
import tk.zhangh.mybatis.domain.Student;
import tk.zhangh.mybatis.util.MyBatisUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * StudentService测试类
 * <p>
 * Created by ZhangHao on 2016/10/20.
 */
public class StudentServiceTest {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceTest.class);
    private static StudentService studentService;

    @BeforeClass
    public static void setup() {
        TestDataPopulator.initDatabase();

        SqlSessionFactory sqlSessionFactory;
//		sqlSessionFactory = MyBatisUtil.getSqlSessionFactoryUsingXML();
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactoryUsingJavaAPI();
        studentService = new StudentService(sqlSessionFactory);
    }

    @AfterClass
    public static void teardown() {
        studentService = null;
    }

    @Test
    public void testFindAllStudents() {
        List<Student> students = studentService.findAllStudents();
        assertNotNull(students);
        for (Student student : students) {
            assertNotNull(student);
            log.info(student.toString());
        }
    }

    @Test
    public void testFindStudentById() {
        Student student = studentService.findStudentById(1);
        assertNotNull(student);
        log.info(student.toString());
    }

    @Test
    public void testCreateUStudent() {
        Student student = new Student();
        int id = 4;
        student.setStudId(id);
        student.setName("student_" + id);
        student.setEmail("student_" + id + "gmail.com");
        student.setPhone(new PhoneNumber("111-111-1111"));
        Student newStudent = studentService.createStudent(student);
        assertNotNull(newStudent);
        assertEquals("student_" + id, newStudent.getName());
        assertEquals("student_" + id + "gmail.com", newStudent.getEmail());
        log.info(newStudent.toString());
    }

    @Test
    public void testUpdateStudent() {
        int id = 2;
        Student student = studentService.findStudentById(id);
        student.setStudId(id);
        student.setName("student_" + id);
        student.setEmail("student_" + id + "gmail.com");
        student.setPhone(new PhoneNumber("222-222-2222"));
        studentService.updateStudent(student);
        Student updatedStudent = studentService.findStudentById(id);
        assertNotNull(updatedStudent);
        assertEquals("student_" + id, updatedStudent.getName());
        assertEquals("student_" + id + "gmail.com", updatedStudent.getEmail());
        log.info(updatedStudent.toString());
    }
}
