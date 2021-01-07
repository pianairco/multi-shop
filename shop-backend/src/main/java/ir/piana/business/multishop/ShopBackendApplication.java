package ir.piana.business.multishop;

import ir.piana.business.multishop.cfg.StaticResourcePropertiesModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@EnableConfigurationProperties({
//		ServiceProperties.class,
//		StorageProperties.class,
//		SqlProperties.class,
		StaticResourcePropertiesModel.class,
//		StoreMenuProperties.class
})
public class ShopBackendApplication {

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
