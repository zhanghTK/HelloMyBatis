package tk.zhangh.mybatis.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import tk.zhangh.mybatis.domain.Student;
import tk.zhangh.mybatis.mappers.StudentMapper;
import tk.zhangh.mybatis.typehandlers.PhoneTypeHandler;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;


/**
 * MyBatis帮助类
 * 分别使用XML和Java API 初始化SqlSessionFactory
 * <p>
 * Created by ZhangHao on 2016/10/20.
 */
public class MyBatisUtil {
    private static SqlSessionFactory xmlSqlSessionFactory;
    private static SqlSessionFactory javaSqlSessionFactory;

    public static SqlSessionFactory getSqlSessionFactoryUsingXML() {
        if (xmlSqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                xmlSqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return xmlSqlSessionFactory;
    }

    public static SqlSessionFactory getSqlSessionFactoryUsingJavaAPI() {
        if (javaSqlSessionFactory == null) {
            try {
                DataSource dataSource = DataSourceFactory.getDataSource();
                TransactionFactory transactionFactory = new JdbcTransactionFactory();
                Environment environment = new Environment("development", transactionFactory, dataSource);
                Configuration configuration = new Configuration(environment);
//                configuration.getTypeAliasRegistry().registerAlias(Student.class);  // 默认注册student
                configuration.getTypeAliasRegistry().registerAlias("student", Student.class);  // 注册student
//                configuration.getTypeAliasRegistry().registerAliases("tk.zhangh.mybatis.domain");  // 为包中所有类注册
//                configuration.getTypeHandlerRegistry().register(PhoneNumber.class, PhoneTypeHandler.class);  // 为特定类注册类助力器
                configuration.getTypeHandlerRegistry().register(PhoneTypeHandler.class);  // 注册一个类处理器
//                configuration.getTypeHandlerRegistry().register("tk.zhangh.mybatis.typehandlers");  // 注册包中所有类型处理器
//                configuration.setXxx(true);  // 全局参数配置
                configuration.addMapper(StudentMapper.class);
                javaSqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return javaSqlSessionFactory;
    }

}
