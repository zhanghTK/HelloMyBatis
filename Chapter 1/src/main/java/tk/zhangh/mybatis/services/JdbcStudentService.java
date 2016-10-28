package tk.zhangh.mybatis.services;


import tk.zhangh.mybatis.domain.Student;

import java.sql.*;
import java.util.Date;

/**
 * 使用JDBC操作Student
 *
 * @author Siva
 */

public class JdbcStudentService {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/elearning";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        JdbcStudentService service = new JdbcStudentService();

        Student existingStudent = service.findStudentById(1);
        System.out.println(existingStudent);


        long ts = System.currentTimeMillis();//For creating unique student names
        Student newStudent = new Student(0, "student_" + ts, "student_" + ts + "@gmail.com", new Date());
        service.createStudent(newStudent);
        System.out.println(newStudent);

        int updateStudId = 1;
        Student updateStudent = service.findStudentById(updateStudId);
        ts = System.currentTimeMillis();//For creating unique student email
        updateStudent.setEmail("student_" + ts + "@gmail.com");
        service.updateStudent(updateStudent);

    }

    public Student findStudentById(int studId) {
        Student student = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getDatabaseConnection();
            String sql = "select * from students where stud_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                student = new Student();
                student.setStudId(rs.getInt("stud_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setDob(rs.getDate("dob"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return student;
    }

    public void createStudent(Student student) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getDatabaseConnection();
            String sql = "INSERT INTO STUDENTS(STUD_ID,NAME,EMAIL,DOB) VALUES(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student.getStudId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getEmail());
            pstmt.setDate(4, new java.sql.Date(student.getDob().getTime()));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateStudent(Student student) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getDatabaseConnection();
            conn = getDatabaseConnection();
            String sql = "UPDATE STUDENTS SET NAME=?,EMAIL=?,DOB=? WHERE STUD_ID=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setDate(3, new java.sql.Date(student.getDob().getTime()));
            pstmt.setInt(4, student.getStudId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getCause());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected Connection getDatabaseConnection() throws SQLException {
        try {
            Class.forName(JdbcStudentService.DRIVER);
            return DriverManager
                    .getConnection(JdbcStudentService.URL, JdbcStudentService.USERNAME, JdbcStudentService.PASSWORD);
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

}
