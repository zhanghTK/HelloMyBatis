package tk.zhangh.mybatis.mappers;

import org.apache.ibatis.annotations.*;
import tk.zhangh.mybatis.domain.Course;
import tk.zhangh.mybatis.domain.Tutor;
import tk.zhangh.mybatis.sqlproviders.TutorDynaSqlProvider;

import java.util.List;

/**
 * Created by ZhangHao on 2016/10/28.
 */
public interface TutorMapper {

    @Select("select * from courses where tutor_id=#{tutorId}")
    @ResultMap("tk.zhangh.mybatis.mappers.TutorMapper.CourseResult")
    List<Course> selectCoursesByTutorId(int tutorId);

    @Select("SELECT tutor_id, t.name as tutor_name, email, addr_id FROM tutors t where t.tutor_id=#{tutorId}")
    @Results({
            @Result(id = true, column = "tutor_id", property = "tutorId"),
            @Result(column = "tutor_name", property = "name"),
            @Result(column = "email", property = "email"),
            @Result(property = "address", column = "addr_id",
                    one = @One(select = "tk.zhangh.mybatis.mappers.AddressMapper.selectAddressById")),
            @Result(property = "courses", column = "tutor_id",
                    many = @Many(select = "tk.zhangh.mybatis.mappers.TutorMapper.selectCoursesByTutorId"))
    })
    Tutor selectTutorWithCoursesById(int tutorId);

    @SelectProvider(type = TutorDynaSqlProvider.class, method = "findAllTutorsSql")
    List<Tutor> findAllTutors();

    @SelectProvider(type = TutorDynaSqlProvider.class, method = "findTutorByIdSql")
    Tutor findTutorById(int tutorId);

    @SelectProvider(type = TutorDynaSqlProvider.class, method = "findTutorByNameAndEmailSql")
    Tutor findTutorByNameAndEmail(@Param("name") String name, @Param("email") String email);

    @InsertProvider(type = TutorDynaSqlProvider.class, method = "insertTutor")
    @Options(useGeneratedKeys = true, keyProperty = "tutorId")
    int insertTutor(Tutor tutor);

    @UpdateProvider(type = TutorDynaSqlProvider.class, method = "updateTutor")
    int updateTutor(Tutor tutor);

    @DeleteProvider(type = TutorDynaSqlProvider.class, method = "deleteTutor")
    int deleteTutor(int tutorId);

    @SelectProvider(type = TutorDynaSqlProvider.class, method = "selectTutorById")
    @ResultMap("tk.zhangh.mybatis.mappers.TutorMapper.TutorResult")
    Tutor selectTutorById(int tutorId);


}
