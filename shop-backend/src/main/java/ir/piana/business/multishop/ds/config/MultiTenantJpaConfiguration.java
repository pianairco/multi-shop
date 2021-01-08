package ir.piana.business.multishop.ds.config;

import com.zaxxer.hikari.HikariDataSource;
import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import org.apache.commons.io.IOUtils;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Profile({ "production"})
@EnableConfigurationProperties({ JpaProperties.class })
@EnableJpaRepositories(
		basePackages = {
				"ir.piana.business.multishop.ds.repository",
				"ir.piana.business.multishop.data.repository"
		},
		transactionManagerRef = "txManager")
@EnableTransactionManagement
public class MultiTenantJpaConfiguration {
	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	private DatabaseConfig databaseConfig;

	@Bean("supportExecutor")
	public SpecificSchemaQueryExecutor getSupportExecutor() throws IOException {
		HikariDataSource supportDs = new HikariDataSource();
		supportDs.setDriverClassName(databaseConfig.getSupport().getDriverClassName());
		supportDs.setJdbcUrl(databaseConfig.getSupport().getUrl());
		supportDs.setUsername(databaseConfig.getSupport().getUsername());
		supportDs.setPassword(databaseConfig.getSupport().getPassword());
		supportDs.setPoolName("Pool-support");
		supportDs.setMaximumPoolSize(databaseConfig.getPoolSize());
		supportDs.setConnectionTimeout(5000);
		supportDs.setIdleTimeout(5000);
		supportDs.setInitializationFailTimeout(5000);

		InputStream resourceAsStream = MultiTenantJpaConfiguration.class.getResourceAsStream("/data.sql");
		String[] split = IOUtils.toString(resourceAsStream).split(";");
		SpecificSchemaQueryExecutor specificSchemaQueryExecutor = new SpecificSchemaQueryExecutor(supportDs);
		for (String script : split) {
			try {
				specificSchemaQueryExecutor.execute(script);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}

		return specificSchemaQueryExecutor;
	}

	@Bean("multiShopDataSources")
	public List<DataSourceEntity> getActiveDataSources() {
		return new ArrayList<>();
	}

	@Bean(name = "failedDataSources" )
	public Map<String, DataSourceEntity> getFailedDataSources() {
		return new LinkedHashMap<>();
	}

	@Bean(name = "dataSources")
	@DependsOn("supportExecutor")
	public Map<String, DataSource> datasources(SpecificSchemaQueryExecutor supportExecutor) {
		LinkedHashMap<String, DataSource> datasourceMap = new LinkedHashMap<>();
		datasourceMap.put("support", supportExecutor.getDatasource());
		return datasourceMap;
	}

	@Bean("entityManagerFactoryBean")
	@DependsOn("dataSources")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			MultiTenantConnectionProvider multiTenantConnectionProvider,
			CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

		Map<String, Object> hibernateProps = new LinkedHashMap<>();
		hibernateProps.putAll(this.jpaProperties.getProperties());
		hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
		hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
		hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

		LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
		result.setPackagesToScan(databaseConfig.getPackagesToScan());
		HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
		vendor.setShowSql(databaseConfig.isShowSql());
		result.setJpaVendorAdapter(vendor);
		result.setJpaPropertyMap(hibernateProps);
		return result;
	}

	@Bean("entityManagerFactory")
	public EntityManagerFactory entityManagerFactory(
			LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return entityManagerFactoryBean.getObject();
	}

	@Bean("txManager")
	public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
}
