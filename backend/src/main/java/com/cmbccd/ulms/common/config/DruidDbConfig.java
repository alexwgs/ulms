package com.cmbccd.ulms.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 
 * <p>
 * Title: DruidDBConfig.java
 * </p>
 * <p>
 * Description:数据库连接配置
 * </p>
 * 
 * @author WeiGenSheng
 * @date 2020年5月5日
 * @version 1.0
 */
@Configuration
public class DruidDbConfig {
	private Logger logger = LoggerFactory.getLogger(DruidDbConfig.class);
	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.druid.initial-size:5}")
	private int initialSize;

	@Value("${spring.datasource.druid.min-idle:5}")
	private int minIdle;

	@Value("${spring.datasource.druid.max-active:20}")
	private int maxActive;

	@Value("${spring.datasource.druid.max-wait:60000}")
	private int maxWait;

	@Value("${spring.datasource.druid.time-between-eviction-runs-millis:60000}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.druid.min-evictable-idle-time-millis:300000}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.druid.validation-query:SELECT 1}")
	private String validationQuery;

	@Value("${spring.datasource.druid.test-while-idle:true}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.druid.test-on-borrow:false}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.druid.test-on-return:false}")
	private boolean testOnReturn;

	@Value("${spring.datasource.druid.pool-prepared-statements:true}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size:20}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.druid.filters:stat,wall,slf4j}")
	private String filters;

	@Value("${spring.datasource.druid.connection-properties:druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000}")
	private String connectionProperties;
	
	@Bean
	@Primary
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(this.dbUrl);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);

		// configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
		datasource.setConnectionProperties(connectionProperties);

		return datasource;
	}

}
