package tk.zhangh.mybatis.services;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.mybatis.domain.PhoneNumber;
import tk.zhangh.mybatis.domain.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by ZhangHao on 2016/10/23.
 */
public class StudentServiceTest {
    private static Logger logger = LoggerFactory.getLogger(StudentServiceTest.class);
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
    public void testFindAllStudents() throws Exception {
        List<Student> students = studentService.findAllStudents();
        assertNotNull(students);
        for (Student student : students) {
            assertNotNull(student);
            logger.info(student.toString());
        }
    }

    @Test
    public void testFindStudentById() throws Exception {
        Student student = studentService.findStudentById(1);
        assertNotNull(student);
        logger.info(student.toString());
    }

    @Test
    public void testFindStudentWithAddressById() throws Exception {
        Student student = studentService.findStudentWithAddressById(1);
        assertNotNull(student);
        logger.info(student.toString());
    }

    @Test
    public void testCreateStudent() throws Exception {
        Student stud = new Student();
        long ts = System.currentTimeMillis();
        stud.setName("stud_" + ts);
        stud.setEmail("stud_" + ts + "@gmail.com");
        stud.setPhone(new PhoneNumber("000-000-0000"));
        Student student = studentService.createStudent(stud);
        assertNotNull(student);
        assertEquals("stud_" + ts, student.getName());
        assertEquals("stud_" + ts + "@gmail.com", student.getEmail());
        logger.info(student.toString());
    }

    @Test
    public void testCreateStudentWithMap() throws Exception {
        List<Student> students = studentService.findAllStudents();
        logger.info("创建前数据库有记录：" + students.size() + "条");
        Map<String, Object> studMap = new HashMap<String, Object>();
        long ts = System.currentTimeMillis();
        studMap.put("name", "stud_" + ts);
        studMap.put("email", "stud_" + ts + "@gmail.com");
        studMap.put("phone", null);
        studentService.createStudentWithMap(studMap);
        List<Student> newStudents = studentService.findAllStudents();
        logger.info("创建后数据库有记录：" + newStudents.size() + "条");
        assertEquals(students.size(), newStudents.size() - 1);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student stud = new Student();
        long ts = System.currentTimeMillis();
        stud.setStudId(2);
        stud.setName("stud_" + ts);
        stud.setEmail("stud_" + ts + "@gmail.com");
        System.out.println("=============================");
        Student updatedStudent = studentService.updateStudent(stud);
        assertNotNull(updatedStudent);
        assertEquals("stud_" + ts, updatedStudent.getName());
        assertEquals("stud_" + ts + "@gmail.com", updatedStudent.getEmail());
        logger.info(updatedStudent.toString());
    }

    @Test
    public void testDeleteStudent() throws Exception {
        List<Student> students = studentService.findAllStudents();
        logger.info("删除前数据库有记录：" + students.size() + "条");
        boolean deleteStudent = studentService.deleteStudent(3);
        assertTrue(deleteStudent);
        List<Student> newStudents = studentService.findAllStudents();
        logger.info("删除后数据库有记录：" + newStudents.size() + "条");
        assertEquals(students.size(), newStudents.size() + 1);
    }
}