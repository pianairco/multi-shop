package ir.piana.business.multishop.ds.config;

import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import ir.piana.business.multishop.ds.service.DataSourceService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

@Configuration
@Profile({ "production"})
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Autowired
	@Qualifier("failedDataSources")
	private Map<String, DataSourceEntity> failedDataSources;

	@Autowired
	@Qualifier("multiShopDataSources")
	private List<DataSourceEntity> sejamDataSources;

	@Autowired
	private DataSourceService dataSourceService;

	@SneakyThrows
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MultiTenantInterceptor(
				sejamDataSources, failedDataSources, dataSourceService));
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
