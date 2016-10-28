package tk.zhangh.mybatis.util;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 获取SqlSessionFactory单例的工具类
 * Created by ZhangHao on 2016/10/18.
 */
public class MyBatisSqlSessionFactory {
    private static final Properties PROPERTIES = new Properties();
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            InputStream is = DataSourceFactory.class.getResourceAsStream("/application.properties");
            PROPERTIES.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession getSqlSession() {
        return getSqlSessionFactory().openSession();
    }

    public static Connection getConnection() {
        String driver = PROPERTIES.getProperty("jdbc.driverClassName");
        String url = PROPERTIES.getProperty("jdbc.url");
        String username = PROPERTIES.getProperty("jdbc.username");
        String password = PROPERTIES.getProperty("jdbc.password");
        Connection connection;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
