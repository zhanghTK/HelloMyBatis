package tk.zhangh.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
/*
import javax.sql.DataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
*/

/**
 * Created by ZhangHao on 2016/10/28.
 */
@Configuration
@MapperScan(value = "tk.zhangh.mybatis.mappers")
public class AppConfig {
    /*
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
	*/
}
