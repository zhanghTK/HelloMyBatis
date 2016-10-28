package tk.zhangh.mybatis.util;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


/**
 * MyBatis帮助类
 * 分别使用XML和Java API 初始化SqlSessionFactory
 * <p>
 * Created by ZhangHao on 2016/10/20.
 */
public class MyBatisUtil {
    private static final String DEFAULT_MYBATIS_CONFIG_FILE = "mybatis-config.xml";
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
            try {
                InputStream inputStream = Resources.getResourceAsStream(DEFAULT_MYBATIS_CONFIG_FILE);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }


    public static Connection getConnection() {
        String driver = PROPERTIES.getProperty("jdbc.driverClassName");
        String url = PROPERTIES.getProperty("jdbc.url");
        String username = PROPERTIES.getProperty("jdbc.username");
        String password = PROPERTIES.getProperty("jdbc.password");
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
