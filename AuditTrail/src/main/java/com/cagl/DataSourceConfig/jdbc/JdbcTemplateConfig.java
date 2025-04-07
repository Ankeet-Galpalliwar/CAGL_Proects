package com.cagl.DataSourceConfig.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class JdbcTemplateConfig {

	@Bean(name = "jdbcTemplate1")
	public NamedParameterJdbcTemplate jdbcTemplate1(@Qualifier("dataSource1") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean(name = "jdbcTemplate2")
	public NamedParameterJdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}
