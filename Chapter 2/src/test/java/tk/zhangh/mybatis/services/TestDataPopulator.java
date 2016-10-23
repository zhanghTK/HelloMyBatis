package tk.zhangh.mybatis.services;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.mybatis.util.DataSourceFactory;

import java.io.Reader;
import java.sql.Connection;

/**
 * 测试数据使用的数据初始化类
 *
 * Created by ZhangHao on 2016/10/20.
 */
public class TestDataPopulator {
    private static Logger logger = LoggerFactory.getLogger(TestDataPopulator.class);

    public static void main(String[] args) {
        initDatabase();
    }

    public static void initDatabase() {
        Connection connection;
        Reader reader;
        try {
            connection = DataSourceFactory.getDataSource().getConnection();
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            reader = Resources.getResourceAsReader("sql/drop_tables.sql");
            scriptRunner.runScript(reader);
            logger.info("drop_tables.sql executed successfully");
            reader = Resources.getResourceAsReader("sql/create_tables.sql");
            scriptRunner.runScript(reader );
            logger.info("create_tables.sql executed successfully");
            reader = Resources.getResourceAsReader("sql/sample_data.sql");
            scriptRunner.runScript(reader );
            logger.info("sample_data.sql executed successfully");
            connection.commit();
            reader.close();
            scriptRunner.closeConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
