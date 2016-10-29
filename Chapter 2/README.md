
Chapter 2: Bootstrapping MyBatis
=======================================

How to Run:
	1. 创建数据库执行src/main/resources/sql目录下脚本
	2. 修改src/main/resources/application.properties目录下配置文件
	3. 运行Junit测试
  4. `StudentServiceTest`类的`setup()`通过注释选择配置方式（默认使用XML配置）

```java
// 使用XML配置
// sqlSessionFactory = MyBatisUtil.getSqlSessionFactoryUsingXML();
	
// Java API配置
// sqlSessionFactory = MyBatisUtil.getSqlSessionFactoryUsingJavaAPI();
```

---

**关于类型处理器**typeHandlers

MyBatis运行时创建PreparedStatement对象，使用setXxx()方式对占位符设置值

例如有mapper.xml形式如下：

```xml
<insert id="insertStudent" parameterType="Student">
	INSERT INTO STUDENTS(STUD_ID,NAME,EMAIL,DOB) 
  	VALUES(#{studId},#{name},#{email},#{dob})
</insert>
```

MyBatis将执行以下动作：

1. 创建带有占位符的PreparedStatement

   ```java
   PreparedStatement pstmt = connection
     .prepareStatement("INSERT INTO STUDENTS(STUD_ID,NAME,EMAIL,DOB) VALUES(?,?,?,?)");
   ```

2. 依此检查Student对象各个属性类型，选择合适的setXxx方法设置值

   ```java
   pstmt.setInt(1,student.getStudId());
   pstmt.setString(2, student.getName());
   pstmt.setString(3, student.getEmail());
   ```

3. MyBaits 会将 java.util.Date 类型转换为 into java.sql.Timestamp 并设值 

   ```java
   pstmt.setTimestamp(4, new Timestamp((student.getDob()).getTime()));
   ```

MyBatis对于以下类型使用内建类型处理器：

基本类型（以及对应包装类型）、byte[]、java.util.Date、java.sql.Date、java.sql.Time、java.sql.Timestamp、枚举类型。

对于其他自定义类型：

1. 需要创建一个自定义的类型处理器，继承`BaseTypeHandler<T>`抽象类
2. 在mybatis-config.xml中注册

