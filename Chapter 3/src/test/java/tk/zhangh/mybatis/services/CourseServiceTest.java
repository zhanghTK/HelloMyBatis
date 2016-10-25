package tk.zhangh.mybatis.services;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.mybatis.domain.Course;

import java.util.*;

/**
 * Created by ZhangHao on 2016/10/23.
 */
public class CourseServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceTest.class);

    private static CourseService courseService;

    @BeforeClass
    public static void setup() {
        TestDataPopulator.initDatabase();
        courseService = new CourseService();
    }

    @AfterClass
    public static void teardown() {
        courseService = null;
    }

    @Test
    public void searchCourses() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tutorId", 1);
        //map.put("courseName", "%java%");
        map.put("startDate", new Date());
        List<Course> courses = courseService.searchCourses(map);
        Assert.assertNotNull(courses);
        for (Course course : courses) {
            Assert.assertNotNull(course);
            logger.info("" + course.toString());
        }
    }

    @Test
    public void searchCoursesByTutors() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Integer> tutorIds = new ArrayList<Integer>();
        tutorIds.add(1);
        tutorIds.add(2);
        map.put("tutorIds", tutorIds);
        map.put("courseName", "%java%");
        map.put("startDate", new Date());
        List<Course> courses = courseService.searchCoursesByTutors(map);
        Assert.assertNotNull(courses);
        for (Course course : courses) {
            Assert.assertNotNull(course);
            logger.info("" + course.toString());
        }
    }
}