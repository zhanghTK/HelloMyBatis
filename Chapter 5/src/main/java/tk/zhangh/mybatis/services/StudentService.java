package tk.zhangh.mybatis.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.zhangh.mybatis.domain.Address;
import tk.zhangh.mybatis.domain.Student;
import tk.zhangh.mybatis.mappers.AddressMapper;
import tk.zhangh.mybatis.mappers.StudentMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhangHao on 2016/10/28.
 */
@Service
@Transactional
public class StudentService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private AddressMapper addressMapper;

    public List<Student> findAllStudents() {
        return studentMapper.findAllStudents();
    }

    public Student findStudentById(Integer id) {
        logger.debug("findStudentById :" + id);
        return studentMapper.findStudentById(id);
    }

    public Student findStudentWithAddressById(int id) {
        return studentMapper.selectStudentWithAddress(id);
    }

    public Student createStudent(Student student) {
        Address address = student.getAddress();
        if (address != null) {
            addressMapper.insertAddress(address);
        }
        if (student.getName() == null || student.getName().trim().length() == 0) {
            throw new RuntimeException("Student Name should not be null");
        }
        studentMapper.insertStudent(student);
        return student;
    }

    public void createStudentWithMap(Map<String, Object> studentDataMap) {
        studentMapper.insertStudentWithMap(studentDataMap);
    }

    public Student updateStudent(Student student) {
        studentMapper.updateStudent(student);
        return student;
    }

    public boolean deleteStudent(int id) {
        int count = studentMapper.deleteStudent(id);
        return count > 0;
    }

    public Map<String, Object> findStudentMapById(int id) {
        return studentMapper.findStudentMapById(id);
    }

    public List<Map<String, Object>> findAllStudentsMap() {
        return studentMapper.findAllStudentsMap();
    }
}
