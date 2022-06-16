package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
	
	@Bean
	public SqlSessionFactory sqlSession(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean SqlSessionFactory = new SqlSessionFactoryBean();
		SqlSessionFactory.setDataSource(dataSource);
		SqlSessionFactory.setConfigLocation(applicationContext.getResource("classPath:com/douzone/mysite/config/app/mybatis/configuration.xml"));
		return SqlSessionFactory.getObject();
	}

}
