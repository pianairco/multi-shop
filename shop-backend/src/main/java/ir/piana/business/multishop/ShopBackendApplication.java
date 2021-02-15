package ir.piana.business.multishop;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ir.piana.business.multishop.cfg.StaticResourcePropertiesModel;
import ir.piana.business.multishop.common.dev.sqlrest.ServiceProperties;
import ir.piana.business.multishop.common.dev.uploadrest.StorageProperties;
import ir.piana.business.multishop.common.dev.service.sql.SqlProperties;
import ir.piana.business.multishop.common.dev.service.store.StoreMenuProperties;
import ir.piana.business.multishop.util.LowerCaseKeyDeserializer;
import ir.piana.business.multishop.util.LowerCaseKeySerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@EnableConfigurationProperties({
		ServiceProperties.class,
		StorageProperties.class,
		SqlProperties.class,
		StaticResourcePropertiesModel.class,
		StoreMenuProperties.class
})
public class ShopBackendApplication {
//	@Bean("spring-resource-chain-cache")
//	public String cacheName() {
//		return "piana";
//	}

//	@Bean("piana-cache")
//	public Cache cache() {
//		return new ConcurrentMapCache("spring-resource-chain-cache",
//				new ConcurrentHashMap<>(), true);
//	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean("jdbcObjectMapper")
	public ObjectMapper getJdbcObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("LowerCaseKeyDeserializer",
				new Version(1,0,0,null));
		module.addKeyDeserializer(Object.class, new LowerCaseKeyDeserializer());
		module.addKeySerializer(Object.class, new LowerCaseKeySerializer());
		objectMapper.registerModule(module);
		return objectMapper;
	}

	@Bean("objectMapper")
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
//		SimpleModule module = new SimpleModule("LowerCaseKeyDeserializer",
//				new Version(1,0,0,null));
//		module.addKeyDeserializer(Object.class, new LowerCaseKeyDeserializer());
//		module.addKeySerializer(Object.class, new LowerCaseKeySerializer());
//		objectMapper.registerModule(module);
		return objectMapper;
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(ShopBackendApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ShopBackendApplication.class);
		app.setAdditionalProfiles("production");
		app.run(args);
//		SpringApplication.run(ShopBackendApplication.class, args);
	}

}
