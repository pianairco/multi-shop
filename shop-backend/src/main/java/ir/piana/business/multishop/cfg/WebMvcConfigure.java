package ir.piana.business.multishop.cfg;

import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import ir.piana.business.multishop.ds.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {
    @Autowired
    private StaticResourcePropertiesModel staticResourceProperties;

    @Autowired
    @Qualifier("failedDataSources")
    private Map<String, DataSourceEntity> failedDataSources;

    @Autowired
    @Qualifier("multiShopDataSources")
    private List<DataSourceEntity> sejamDataSources;

    @Autowired
    private DataSourceService dataSourceService;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        staticResourceProperties.getPaths().forEach((k, v) -> {
            registry.addResourceHandler(k)
                    .addResourceLocations(v.toArray(new String[0]));
//                    .addResourceLocations("classpath:/static/", "file:///c:/upload-dir/");
        });
    }
}
