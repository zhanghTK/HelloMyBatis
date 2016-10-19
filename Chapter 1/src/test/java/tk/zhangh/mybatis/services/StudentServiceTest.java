package tk.zhangh.mybatis.services;

import lombok.extern.java.Log;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tk.zhangh.mybatis.domain.Student;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * StudentService测试类
 * Created by ZhangHao on 2016/10/18.
 */
@Log
public class StudentServiceTest {
    private static StudentService studentService;

    @BeforeClass
    public static void setup() {
        studentService = new StudentService();
        TestDataPopulator.initDatabase();
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
    public void testFindStudentById()
    {
        Student student = studentService.findStudentById(1);
        assertNotNull(student);
        log.info(student.toString());
    }

    @Test
    public void testCreateUStudent()
    {
        Student student = new Student();
        int id = 4;
        student.setStudId(id);
        student.setName("student_"+id);
        student.setEmail("student_"+id+"gmail.com");
        student.setDob(new Date());
        log.info(student.toString());
        studentService.createStudent(student);
        Student newStudent = studentService.findStudentById(id);
        assertNotNull(newStudent);
        assertEquals("student_" + id, newStudent.getName());
        assertEquals("student_"+id+"gmail.com", newStudent.getEmail());
        log.info(newStudent.toString());
    }

    @Test
    public void testUpdateStudent()
    {
        int id = 2;
        Student student =studentService.findStudentById(id);
        student.setStudId(id);
        student.setName("student_"+id);
        student.setEmail("student_"+id+"gmail.com");
        Date now = new Date();
        student.setDob(now);
        studentService.updateStudent(student);
        Student updatedStudent = studentService.findStudentById(id);
        assertNotNull(updatedStudent);
        assertEquals("student_"+id, updatedStudent.getName());
        assertEquals("student_"+id+"gmail.com", updatedStudent.getEmail());
    }
}