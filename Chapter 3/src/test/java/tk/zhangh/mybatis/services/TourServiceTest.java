package tk.zhangh.mybatis.services;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.mybatis.domain.Tutor;

/**
 * Created by ZhangHao on 2016/10/23.
 */
public class TourServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(TourServiceTest.class);
    private static TutorService tutorService;

    @BeforeClass
    public static void setup() {
        TestDataPopulator.initDatabase();
        tutorService = new TutorService();
    }

    @AfterClass
    public static void teardown() {
        tutorService = null;
    }


    @Test
    public void testFindTutorById() {
        Tutor tutor = tutorService.findTutorById(1);
        tutorService.findTutorById2(1);
//        logger.info(tutor.toString());
//        Assert.assertNotNull(tutor);
//        List<Course> courses = tutor.getCourses();
//
//        Assert.assertNotNull(courses);
//        for (Course course : courses) {
//            Assert.assertNotNull(course);
//            logger.info(course.toString());
//        }
    }
}