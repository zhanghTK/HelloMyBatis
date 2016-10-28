package tk.zhangh.mybatis.services;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.mybatis.domain.Tutor;
import tk.zhangh.mybatis.mappers.TutorMapper;
import tk.zhangh.mybatis.util.MyBatisUtil;

/**
 * Created by ZhangHao on 2016/10/23.
 */
public class TutorService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Tutor findTutorById(int tutorId) {
        logger.debug("findTutorById :" + tutorId);
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
            return mapper.selectTutorById(tutorId);
        } finally {
            sqlSession.close();
        }
    }

    public Tutor findTutorById2(int tutorId) {
        logger.debug("findTutorById :" + tutorId);
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
            return mapper.selectTutorWithCourses(tutorId);
        } finally {
            sqlSession.close();
        }
    }
}
