
Chapter 1: Getting started with MyBatis
=======================================

How to Run:
	1. 创建数据库执行src/main/resources/sql目录下脚本
	2. 修改src/main/resources/application.properties目录下配置文件
	3. 运行Junit测试

**问题** ：启动后`StudentMapper.xml`文件找不到

**解决** : mapper在resources目录下，文件完整的路径是`tk/zhangh/mybatis/mappers/StudentMapper.xml` 。我使用的IDE是IntelliJ，在创建`tk/zhangh/mybatis/mappers`目录层级的时候按照以往创建包名的习惯直接创建了`tk.zhangh.mybatis.mappers`目录。启动后找不到mapper文件，反复检查配置都没有问题。突然想到这点，重新创建目录结构，解决

**MyBatis简易开发环境搭建** ：

1. 建表
2. 新建Maven项目，添加依赖
3. 配置log4j.properties文件
4. 创建mybatis-config.xml
5. 创建MyBatisSqlSessionFactory单例类，可选
6. 创建Xxx实体类
7. 创建XxxMapper接口
8. 创建XxxMapper.xml
9. 创建XxxService接口
10. 创建单元测试

**MyBatis基本的运行过程** ：

1. 使用mybatis-config.xml内的配置信息创建SqlSessionFactory对象，根据mapper.xml与mapper接口生成对应的mapper对象
2. 使用sqlSessionFactory获得sqlSession
3. 使用sqlSession获得一个mapper接口的实例
4. 面向mapper接口进行数据操作

注意：

1. XxxMapper.xml与XxxMapper.java相对应

   接口的完全限定名与xml文件中namespace值一致

   接口的方法名与xml文件中id值一致

2. sqlSession不是线程安全的，作用域建议在方法作用域上，对应Web的request级别的作用域