How to Run:
	1. 创建数据库执行src/main/resources/sql目录下脚本
	2. 修改src/main/resources/application.properties目录下配置文件
	3. 运行Junit测试
	
# 与Spring集成

两种配置方式：Java API和XML+注解

**Java API**

```java
@Configuration
@MapperScan(value = "tk.zhangh.mybatis.mappers")
public class AppConfig {
    
    @Bean
   public DataSource dataSource() {
      return new PooledDataSource("com.mysql.jdbc.Driver", 
                           "jdbc:mysql://localhost:3306/elearning", 
                           "root", "admin");
   }
   
   @Bean
   public SqlSessionFactory sqlSessionFactory() throws Exception {
       SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
       sessionFactory.setDataSource(dataSource());
       return sessionFactory.getObject();
   }
   
}
```

**XML**

```xml
<context:annotation-config/>
<context:component-scan base-package="tk.zhangh.mybatis"/>
<context:property-placeholder location="classpath:application.properties"/>

<tx:annotation-driven transaction-manager="transactionManager"/>
<bean id="transactionManager"
      class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>

<mybatis:scan base-package="tk.zhangh.mybatis.mappers"/>

<!-- 一个线程安全的Bean，可以从中获取sql session-->
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
    <constructor-arg index="0" ref="sqlSessionFactory"/>
</bean>

<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <!--<property name="typeAliases" value=","/>-->
    <property name="typeAliasesPackage" value="tk.zhangh.mybatis.domain"/>
    <property name="typeHandlersPackage" value="tk.zhangh.mybatis.typehandlers"/>
    <property name="mapperLocations" value="classpath*:tk/zhangh/mybatis/**/*.xml"/>
</bean>

<bean id="dataSource"
      class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <property name="driverClassName" value="${jdbc.driverClassName}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```

**注解**

```java
@Service
@Transactional
public class StudentService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private AddressMapper addressMapper;
	// ....
}
```