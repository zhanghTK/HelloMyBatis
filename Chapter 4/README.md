Chapter 4: SQL Mappers using Annotations
========================================

How to Run:
	1. 创建数据库执行src/main/resources/sql目录下脚本
	2. 修改src/main/resources/application.properties目录下配置文件
	3. 运行Junit测试
	
# 使用注解配置SQL映射器

## 映射语句

### @Insert 

```java
//    insert语句，返回插入行数
    @Insert("insert into students(name,email,addr_id, phone) values(#{name},#{email},#{address.addrId},#{phone})")
//    指定主键，并且自动生成主键
    @Options(useGeneratedKeys = true, keyProperty = "studId")
    void insertStudent(Student student);
```

### @Update

```java
//    update语句
    @Update("update students set name=#{name}, email=#{email}, phone=#{phone} where stud_id=#{studId}")
    void updateStudent(Student student);
```

### @Delete

```java
@Delete("delete from students where stud_id=#{studId}")
int deleteStudent(int studId);
```

### @Select

```java
@Select("select stud_id as studId, name, email, addr_id as 'address.addrId', phone from students where stud_id=#{id}")
Student findStudentById(Integer id);
```

## 结果映射

`@Results`注解与配置xml元素`<resultMap>`对应

```java
@Results({
        @Result(id = true, column = "stud_id", property = "studId"),
        @Result(column = "name", property = "name"),
        @Result(column = "email", property = "email"),
        @Result(column = "addr_id", property = "address.addrId")
})
List<Student> findAllStudents();
```

使用注解声明的`ResultMap`不能指定Id来复用。可以在xml中声明，在注解中复用。

```xml
<resultMap type="Address" id="AddressResult">
    <id property="addrId" column="addr_id"/>
    <result property="street" column="street"/>
    <result property="city" column="city"/>
    <result property="state" column="state"/>
    <result property="zip" column="zip"/>
    <result property="country" column="country"/>
</resultMap>
```

```java
@Select("select stud_id, name, email, a.addr_id, street, city, state, zip, country" +
        " FROM students s left outer join addresses a on s.addr_id=a.addr_id" +
        " where stud_id=#{studId} ")
@ResultMap("tk.zhangh.mybatis.mappers.StudentMapper.StudentWithAddressResult")
Student selectStudentWithAddress(int studId);
```

### 一对一映射

**使用`@one`嵌套select语句：**

```java
@Select("select addr_id as addrId, street, city, state, zip, country from addresses where addr_id=#{id}")
Address selectAddressById(int id);

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
```

发出的SQL：

```sql
SELECT tutor_id, t.name as tutor_name, email, addr_id FROM tutors t where t.tutor_id=? 

select addr_id as addrId, street, city, state, zip, country from addresses where addr_id=? 

select * from courses where tutor_id=? 
```

### 一对多映射

使用`@Many`嵌套查询

```java
@Select("select addr_id as addrId, street, city, state, zip, country from addresses where addr_id=#{id}")
Address selectAddressById(int id);

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
```

## 动态SQL

### @SelectProvider

```java
@SelectProvider(type = TutorDynaSqlProvider.class, method = "findTutorByIdSql")
Tutor findTutorById(int tutorId);
```

```java
// SELECT tutor_id as tutorId, name, email FROM tutors WHERE (tutor_id=1) 
public String findTutorByIdSql(final int tutorId) {
  return new SQL() {{
    SELECT("tutor_id as tutorId, name, email");
    FROM("tutors");
    WHERE("tutor_id=" + tutorId);
  }}.toString();
}

// SELECT tutor_id as tutorId, name, email FROM tutors WHERE (tutor_id = ?) 
public String findTutorByIdSql() {
  return new SQL() {{
    SELECT("tutor_id as tutorId, name, email");
    FROM("tutors");
    WHERE("tutor_id = #{tutorId}");
  }}.toString();
}
```

多参数处理：

```java
@SelectProvider(type = TutorDynaSqlProvider.class, method = "findTutorByNameAndEmailSql")
Tutor findTutorByNameAndEmail(@Param("name") String name, @Param("email") String email);
```

```java
// SELECT tutor_id as tutorId, name, email FROM tutors WHERE (name='Paul' AND email='paul@gmail.com') 
public String findTutorByNameAndEmailSql(Map<String, Object> map) {
  final String name = (String) map.get("name");
  final String email = (String) map.get("email");
  return new SQL() {{
    SELECT("tutor_id as tutorId, name, email");
    FROM("tutors");
    WHERE("name='" + name + "' AND email='" + email + "'");
  }}.toString();
}

// SELECT tutor_id as tutorId, name, email FROM tutors WHERE (name=? AND email=?) 
public String findTutorByNameAndEmailSql() {
  return new SQL() {{
    SELECT("tutor_id as tutorId, name, email");
    FROM("tutors");
    WHERE("name=#{name} AND email=#{email}");
  }}.toString();
}
```

在SQL工具类中还包含其他DB操作的方法，参见：`AbstractSQL`

### @InsertProvider

```java
@InsertProvider(type = TutorDynaSqlProvider.class, method = "insertTutor")
@Options(useGeneratedKeys = true, keyProperty = "tutorId")
int insertTutor(Tutor tutor);
```

```java
public String insertTutor(final Tutor tutor) {
  return new SQL() {{
    INSERT_INTO("TUTORS");
    if (tutor.getName() != null) {
      VALUES("NAME", "#{name}");
    }
    if (tutor.getEmail() != null) {
      VALUES("EMAIL", "#{email}");
    }
  }}.toString();
}
```

### @UpdateProvider

```java
@UpdateProvider(type = TutorDynaSqlProvider.class, method = "updateTutor")
int updateTutor(Tutor tutor);
```

```java
public String updateTutor(final Tutor tutor) {
  return new SQL() {{
    UPDATE("TUTORS");
    if (tutor.getName() != null) {
      SET("NAME = #{name}");
    }
    if (tutor.getEmail() != null) {
      SET("EMAIL = #{email}");
    }
    WHERE("TUTOR_ID = #{tutorId}");
  }}.toString();
}
```

### @DeleteProvider

```java
@DeleteProvider(type = TutorDynaSqlProvider.class, method = "deleteTutor")
int deleteTutor(int tutorId);
```

```java
public String deleteTutor(int tutorId) {
    return new SQL() {{
        DELETE_FROM("TUTORS");
        WHERE("TUTOR_ID = #{tutorId}");
    }}.toString();
}
```
