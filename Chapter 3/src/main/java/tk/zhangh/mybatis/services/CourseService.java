package tk.zhangh.mybatis.services;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.mybatis.domain.Course;
import tk.zhangh.mybatis.mappers.CourseMapper;
import tk.zhangh.mybatis.util.MyBatisUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhangHao on 2016/10/23.
 */
public class CourseService {
    private Logger logger = LoggerFactory.getLogger(CourseService.class);

    public List<Course> searchCourses(Map<String, Object> map) {
        logger.debug("searchCourses By :" + map);
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.searchCourses(map);
        } finally {
            sqlSession.close();
        }
    }

    public List<Course> searchCoursesByTutors(Map<String, Object> map) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.searchCoursesByTutors(map);
        } finally {
            sqlSession.close();
        }
    }
}
