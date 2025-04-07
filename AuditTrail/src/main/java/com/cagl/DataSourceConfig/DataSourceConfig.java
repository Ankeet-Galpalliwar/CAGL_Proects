package com.cagl.DataSourceConfig;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public class DataSourceConfig {
	
	@Autowired
	private Environment env;

	private LocalContainerEntityManagerFactoryBean configureEntityManagerFactory(DataSource dataSource, String packagesToScan, String persistenceUnit) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPackagesToScan(new String[] {packagesToScan });
		entityManagerFactoryBean.setPersistenceUnitName(persistenceUnit);

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
//		properties.put("spring.jpa.hibernate.ddl-auto ", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.put("spring.jpa.properties.hibernate.dialect",
				env.getProperty("spring.jpa.properties.hibernate.dialect"));
		entityManagerFactoryBean.setJpaPropertyMap(properties);

		return entityManagerFactoryBean;
	}

	@Bean(name = "dataSource1")
//	@ConfigurationProperties(prefix = "spring.datasource1")
	public DataSource dataSource1() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource1.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource1.url"));
		dataSource.setUsername(env.getProperty("spring.datasource1.username"));
		dataSource.setPassword(env.getProperty("spring.datasource1.password"));
		return dataSource;
//		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dataSource2")
//	@ConfigurationProperties(prefix = "spring.datasource2")
	public DataSource dataSource2() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource2.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource2.url"));
		dataSource.setUsername(env.getProperty("spring.datasource2.username"));
		dataSource.setPassword(env.getProperty("spring.datasource2.password"));
		return dataSource;
//		return DataSourceBuilder.create().build();
	}

	@Bean(name = "entityManagerFactory1")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory1(
			@Qualifier("dataSource1") DataSource dataSource) {

		return configureEntityManagerFactory(dataSource, "com.cagl.entity.datasource1", "dataSource1");

	}
	
	
	
	 @Primary
	    @Bean(name = "transactionManager1")
	    public PlatformTransactionManager transactionManager1(
	            @Qualifier("entityManagerFactory1") EntityManagerFactory entityManagerFactory) {
	        return new JpaTransactionManager(entityManagerFactory);
	    }
	

	@Bean(name = "entityManagerFactory2")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory2(
			@Qualifier("dataSource2") DataSource dataSource) {
		return configureEntityManagerFactory(dataSource,"com.cagl.entity.datasource2", "dataSource2");
	}
	
	
	 @Bean(name = "transactionManager2")
	    public PlatformTransactionManager transactionManager2(
	            @Qualifier("entityManagerFactory2") EntityManagerFactory entityManagerFactory) {
	        return new JpaTransactionManager(entityManagerFactory);
	    }
	 
	 
	
	
	
	
	
	
	

}
