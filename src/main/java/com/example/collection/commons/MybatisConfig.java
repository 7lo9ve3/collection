package com.example.collection.commons;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

//application.properties 설정 대신 좀 더 상세한 설정을 위한 Config
@Configuration
//MyBatis를 이용하여 불러올 메서드가 선언된 인터페이스의 경로를 지정
@MapperScan(basePackages = {"com.example.collection.mapper"}, sqlSessionFactoryRef="sqlSessionFactory", sqlSessionTemplateRef="sqlSessionTemplate")
public class MybatisConfig {

	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource); //! 직접 빈설정을 통해 DataSource를 연결함(@Configuration 어노테이션(수동)과 관련이 있는 듯함
		sqlSessionFactoryBean.setTypeAliasesPackage("com.example.collection.models.entities");
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*-mapper.xml")); //실제 쿼리(Mapper 쿼리)가 들어갈 xml패키지 경로
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name="sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
