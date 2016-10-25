## mapper.xml与mapper.java

| mapper.xml       | mapper接口 |                                     |
| ---------------- | -------- | ----------------------------------- |
| namespace        | 接口完全限定名  |                                     |
| id               | 方法名      |                                     |
| parameterType    | 方法参数     | xml中使用完全限定名或注册的别名                   |
| resultType       | 返回值类型    | xml中使用完全限定名或注册的别名，不可与resultType同时使用 |
| useGeneratedKeys |          | 自动生成主键                              |
| ResultMaps       | 返回值类型    | xml中使用完全限定名或注册的别名，不可与resultType同时使用 |
|                  |          |                                     |

两种调用形式：

```java
Student student = sqlSession
  .selectOne("tk.zhangh.mybatis.mappers.StudentMapper.findStudentById", studId);
```

```java
StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
Student student = studentMapper.findStudentById(studId);
```

## 映射语句

### INSERT

主键生成方式配置

## Select子句

### 返回集合接口的默认实现：
- 对于 List，Collection，Iterable 类型，MyBatis 将返回 java.util.ArrayList
- 对于 Map 类型，MyBatis 将返回 java.util.HashMap
- 对于 Set 类型，MyBatis 将返回 java.util.HashSet
- 对于 SortedSet 类型，MyBatis 将返回 java.util.TreeSe

###  结果集映射—ResultMap

1. resultType：用于查询字段的名称与指定类型字段名称完全对应的情况
2. resultMaps：字段名称不对应或者构建复杂类型

#### 映射查询字段与结果集字段

```xml
<resultMap id="StudentResult" type="tk.zhangh.mybatis.domain.Student">
  <!--id唯一，一般用于标记主键-->
  <!--映射DB字段与结果集字段-->
  <id property="studId" column="stud_id" />
  <result property="name" column="name" />
  <result property="email" column="email" />
  <result property="phone" column="phone" />
</resultMap>
```

对应的查询：

```xml
<select id="findStudentById" parameterType="int" resultMap="StudentResult">
  SELECT * FROM STUDENTS WHERE STUD_ID=#{studId}
</select>
```

#### 构建复杂类型

```xml
<resultMap type="Student" id="StudentWithAddressResult" extends="StudentResult">
  <result property="address.addrId" column="addr_id" />
  <result property="address.street" column="street" />
  <result property="address.city" column="city" />
  <result property="address.state" column="state" />
  <result property="address.zip" column="zip" />
  <result property="address.country" column="country" />
</resultMap>
```

`StudentWithAddressResult`扩展了`StudentResult`，对应的查询：

```xml
<select id="selectStudentWithAddress" 
        parameterType="int"resultMap="StudentWithAddressResult">
  SELECT STUD_ID, NAME, EMAIL, PHONE, A.ADDR_ID, STREET, CITY, STATE, ZIP, COUNTRY
  FROM STUDENTS S LEFT OUTER JOIN ADDRESSES A ON S.ADDR_ID=A.ADDR_ID
  WHERE STUD_ID=#{studId}
</select>
```

### 一对一映射

一个`Student`映射一个`Address`类型

#### 简单一对一映射

构建复杂类型例子就是一个一对一的映射

#### 嵌套结果ResultMap实现一对一

关联独立的resultMap

```xml
<resultMap type="Address" id="AddressResult">
  <id property="addrId" column="addr_id" />
  <result property="street" column="street" />
  <result property="city" column="city" />
  <result property="state" column="state" />
  <result property="zip" column="zip" />
  <result property="country" column="country" />
</resultMap>

<resultMap type="Student" id="StudentWithAddressResult">
  <id property="studId" column="stud_id" />
  <result property="name" column="name" />
  <result property="email" column="email" />
  <association property="address" resultMap="AddressResult" />
</resultMap>

<select id="findStudentWithAddress" parameterType="int" 
        resultMap="StudentWithAddressResult">
  SELECT STUD_ID, NAME, EMAIL, A.ADDR_ID, STREET, CITY, STATE, ZIP, COUNTRY
  FROM STUDENTS S LEFT OUTER JOIN ADDRESSES A ON S.ADDR_ID=A.ADDR_ID
  WHERE STUD_ID=#{studId}
</select>
```

或者定义内联的resultMap

```xml
<resultMap type="Student" id="StudentWithAddressResult">
  <id property="studId" column="stud_id" />
  <result property="name" column="name" />
  <result property="email" column="email" />
  <association property="address" javaType="Address">
    <id property="addrId" column="addr_id" />
    <result property="street" column="street" />
    <result property="city" column="city" />
    <result property="state" column="state" />
    <result property="zip" column="zip" />
    <result property="country" column="country" />
  </association>
</resultMap>
```

#### 嵌套查询实现一对一

```xml
<resultMap type="Address" id="AddressResult">
  <id property="addrId" column="addr_id" />
  <result property="street" column="street" />
  <result property="city" column="city" />
  <result property="state" column="state" />
  <result property="zip" column="zip" />
  <result property="country" column="country" />
</resultMap>

<select id="findAddressById" parameterType="int" resultMap="AddressResult">
  SELECT * FROM ADDRESSES WHERE ADDR_ID=#{id}
</select>

<resultMap type="Student" id="StudentWithAddressResult">
  <id property="studId" column="stud_id" />
  <result property="name" column="name" />
  <result property="email" column="email" />
  <association property="address" column="addr_id" select="findAddressById" />
</resultMap>

<select id="findStudentWithAddress" parameterType="int"
        resultMap="StudentWithAddressResult">
  SELECT * FROM STUDENTS WHERE STUD_ID=#{Id}
</select>
```

###一对多映射

一个`Tutor`映射多个（一个List形式的）`Course`类型

#### 内嵌结果实现ResultMap实现一对一

```xml
<resultMap type="Course" id="CourseResult">
  <id column="course_id" property="courseId" />
  <result column="name" property="name" />
  <result column="description" property="description" />
  <result column="start_date" property="startDate" />
  <result column="end_date" property="endDate" />
</resultMap>

<resultMap type="Tutor" id="TutorResult">
  <id column="tutor_id" property="tutorId" />
  <result column="tutor_name" property="name" />
  <result column="email" property="email" />
  <collection property="courses" resultMap="CourseResult" />
</resultMap>

<select id="findTutorById" parameterType="int" resultMap="TutorResult">
  SELECT T.TUTOR_ID,T.NAME AS TUTOR_NAME,
  	EMAIL,C.COURSE_ID,C.NAME,DESCRIPTION,START_DATE,END_DATE
  FROM TUTORS T LEFT OUTER JOIN COURSES C ON T.TUTOR_ID=C.TUTOR_ID
  WHERE T.TUTOR_ID=#{tutorId}
</select>
				
```

#### 嵌套查询实现一对多

```xml
<resultMap type="Course" id="CourseResult">
  <id column="course_id" property="courseId" />
  <result column="name" property="name" />
  <result column="description" property="description" />
  <result column="start_date" property="startDate" />
  <result column="end_date" property="endDate" />
</resultMap>

<resultMap type="Tutor" id="TutorResult">
  <id column="tutor_id" property="tutorId" />
  <result column="tutor_name" property="name" />
  <result column="email" property="email" />
  <collection property="courses" column="tutor_id" select="findCoursesByTutor" />
</resultMap>

<select id="findTutorById" parameterType="int" resultMap="TutorResult">
    SELECT T.TUTOR_ID, T.NAME AS TUTOR_NAME, EMAIL
    FROM TUTORS T WHERE T.TUTOR_ID=#{tutorId}
</select>
<select id="findCoursesByTutor" parameterType="int" resultMap="CourseResult">
    SELECT * FROM COURSES WHERE TUTOR_ID=#{tutorId}
</select>
```

**嵌套查询问题**

> 嵌套 Select 语句查询会导致 N+1 选择问题。首先,主查询将会执行(1 次),对于主查询返回的每一行,另外一个查询将会被执行(主查询 N 行,则此查询 N 次)。对于大型数据库而言,这会导致很差的性能问题。

## 动态SQL

### IF

```xml
<resultMap type="Course" id="CourseResult">
  <id column="course_id" property="courseId" />
  <result column="name" property="name" />
  <result column="description" property="description" />
  <result column="start_date" property="startDate" />
  <result column="end_date" property="endDate" />
</resultMap>

<!--SELECT * FROM COURSES WHERE TUTOR_ID= ? AND NAME like ? AND START_DATE >= ?-->
<select id="searchCourses" parameterType="hashmap" resultMap="CourseResult"></select>
    SELECT * FROM COURSES WHERE TUTOR_ID= #{tutorId}
    <if test="courseName != null">
      AND NAME LIKE #{courseName}
    </if>
    <if test="startDate != null">
      AND START_DATE >= #{startDate}
    </if>
    <if test="endDate != null">
      AND END_DATE <= #{endDate}
    </if>
</select>
```

调用：

```java
Map<String, Object> map = new HashMap<String, Object>();
map.put("tutorId", 1);
map.put("courseName", "%java%");
map.put("startDate", new Date());

CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
List<Course> courses = mapper.searchCourses(map);
```

### CHOOSE,WHEN,OTHERWISE

含义类似于switch,case,default，并且每个case分支都是带break的：

```xml
<select id="searchCourses" parameterType="hashmap" resultMap="CourseResult">
    SELECT * FROM COURSES
    <choose>
        <when test="searchBy == 'Tutor'">
            WHERE TUTOR_ID= #{tutorId}
        </when>
        <when test="searchBy == 'CourseName'">
            WHERE name like #{courseName}
        </when>
        <otherwise>
            WHERE TUTOR start_date >= now()
        </otherwise>
    </choose>
</select>
```

### WHERE

当所有的查询条件都是可选的的时候使用：

```xml
<select id="searchCourses" parameterType="hashmap" resultMap="CourseResult">
  SELECT * FROM COURSES
  <where>
    <if test=" tutorId != null ">
      TUTOR_ID= #{tutorId}
    </if>
    <if test="courseName != null">
      AND name like #{courseName}
    </if>
    <if test="startDate != null">
      AND start_date >= #{startDate}
    </if>
    <if test="endDate != null">
      AND end_date <= #{endDate}
    </if>
  </where>
</select>
```

### TRIM

```xml
<select id="searchCourses" parameterType="hashmap" resultMap="CourseResult">
  SELECT * FROM COURSES
  <trim prefix="WHERE" prefixOverrides="AND | OR">
    <if test=" tutorId != null ">
      TUTOR_ID= #{tutorId}
    </if>
    <if test="courseName != null">
      AND name like #{courseName}
    </if>
  </trim>
</select>
```

### FOREACH

```xml
<select id="searchCoursesByTutors" parameterType="map" resultMap="CourseResult">
  SELECT * FROM COURSES
  <if test="tutorIds != null">
    <where>
      <foreach item="tutorId" collection="tutorIds">
        OR tutor_id=#{tutorId}
      </foreach>
    </where>
  </if>
</select>
```

```java
Map<String, Object> map = new HashMap<String, Object>();
List<Integer> tutorIds = new ArrayList<Integer>();
tutorIds.add(1);
tutorIds.add(3);
tutorIds.add(6);
map.put("tutorIds", tutorIds);
CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
List<Course> courses = mapper.searchCoursesByTutors(map);
```

FOREACH生成IN子句：

```xml
<select id="searchCoursesByTutors" parameterType="map" resultMap="CourseResult">
    SELECT * FROM COURSES
    <if test="tutorIds != null">
      <where>
        tutor_id IN
        <foreach item="tutorId" collection="tutorIds" open="(" separator="," close=")">
          #{tutorId}
        </foreach>
      </where>
  </if>
</select>
```

### SET

用于update的where

```xml
<update id="updateStudent" parameterType="Student">
  update students
  <set>
    <if test="name != null">name=#{name},</if>
    <if test="email != null">email=#{email},</if>
    <if test="phone != null">phone=#{phone},</if>
  </set>
  where stud_id=#{id}
</update>
```

## 其它支持

### 枚举

默认不需要做任何处理，如果希望数据库存储顺序位置而不是名称需要注册枚举：

```xml
<typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler"
             javaType="tk.zhangh.mybatis.domain.Gender"/>
```

### CLOB/BLOB

- CLOB 类型的列映射到 java.lang.String 类型上
- BLOB 列映射到 byte[] 类型上

  ### 多个参数			

```xml
<select id="findAllStudentsByNameEmail" resultMap="StudentResult">
  select stud_id, name,email, phone 
  from Students
  where name=#{param1} and email=#{param2}
</select>
```

### 多行结果集映射成 Map

```xml
<select id=" findAllStudents" resultMap="StudentResult">
  select * from Students
</select>
```

```java
Map<Integer, Student> studentMap =
sqlSession.selectMap("tk.zhangh.mybatis.mappers.StudentMapper.findAllStudents", "studId");
		
```

### 使用 RowBounds 对结果集进行分页

```xml
<select id="findAllStudents" resultMap="StudentResult">
  select * from Students
</select>  
```

```java
int offset =0 , limit =25;
RowBounds rowBounds = new RowBounds(offset, limit);
List<Student> = studentMapper.getStudents(rowBounds);
```

### 使用 ResultSetHandler 自定义结果集 ResultSet 处理

## 缓存

- 一级缓存默认情况下,启用一级缓存;即,如果你使用同一个 SqlSession接口对象调用了相同的 SELECT 语句,则直接会从缓存中返回结果,而不是再查询一次数据库

- SQL 映射器 XML 配置文件中使用<cache />元素添加全局二级缓存

  - 所有的在映射语句文件定义的<select>语句的查询结果都会被缓存
  - 所有的在映射语句文件定义的<insert>,<update> 和<delete>语句将会刷新缓存
  - 缓存根据最近最少被使用(Least Recently Used,LRU)算法管理
  - 缓存不会被任何形式的基于时间表的刷新(没有刷新时间间隔),即不支持定时刷新机制
  - 缓存将存储1024个 查询方法返回的列表或者对象的引用
  - 缓存会被当作一个读/写缓存。这是指检索出的对象不会被共享,并且可以被调用者安全地修改,不会其他潜在的调用者或者线程的潜在修改干扰。(即,缓存是线程安全的)

定义缓存行为：

```xml
<cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true"/>
```

默认的映射语句的 cache 配置：

```xml
<select ... flushCache="false" useCache="true"/>
<insert ... flushCache="true"/>
<update ... flushCache="true"/>
<delete ... flushCache="true"/>
```


​				
​			
​		
​	
