package ir.piana.business.multishop;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ir.piana.business.multishop.common.cfg.StaticResourcePropertiesModel;
import ir.piana.business.multishop.common.dev.sqlrest.ServiceProperties;
import ir.piana.business.multishop.common.dev.uploadrest.StorageProperties;
import ir.piana.business.multishop.common.dev.service.sql.SqlProperties;
import ir.piana.business.multishop.common.dev.service.store.StoreMenuProperties;
import ir.piana.business.multishop.common.util.LowerCaseKeyDeserializer;
import ir.piana.business.multishop.common.util.LowerCaseKeySerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = {
		/*JmsTemplate.class,*/
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
//@EnableCaching
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
