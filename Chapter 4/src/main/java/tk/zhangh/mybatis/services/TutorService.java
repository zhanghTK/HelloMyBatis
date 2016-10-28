package tk.zhangh.mybatis.services;


import org.apache.ibatis.session.SqlSession;
import tk.zhangh.mybatis.domain.Tutor;
import tk.zhangh.mybatis.mappers.TutorMapper;
import tk.zhangh.mybatis.util.MyBatisUtil;

import java.util.List;

/**
 * Created by ZhangHao on 2016/10/28.
 */
public class TutorService {
    public List<Tutor> findAllTutors() {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
            return mapper.findAllTutors();
        } finally {
            sqlSession.close();
        }
    }

    public Tutor findTutorById(int tutorId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
            return mapper.findTutorById(tutorId);
        } finally {
            sqlSession.close();
        }
    }

    public Tutor findTutorByNameAndEmail(String name, String email) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
            return mapper.findTutorByNameAndEmail(name, email);
        } finally {
            sqlSession.close();
        }
    }

    public Tutor createTutor(Tutor tutor) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
            mapper.insertTutor(tutor);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tutor;
    }

    public Tutor updateTutor(Tutor tutor) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
            mapper.updateTutor(tutor);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tutor;
    }

    public boolean deleteTutor(int tutorId) {
        boolean deleted = false;
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
            int nor = mapper.deleteTutor(tutorId);
            deleted = (nor == 1);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return deleted;
    }

    public Tutor selectTutorById(int tutorId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
            return mapper.selectTutorById(tutorId);
        } finally {
            sqlSession.close();
        }
    }

    public Tutor selectTutorWithCoursesById(int tutorId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
            return mapper.selectTutorWithCoursesById(tutorId);
        } finally {
            sqlSession.close();
        }
    }
}
