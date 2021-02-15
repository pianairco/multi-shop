package ir.piana.business.multishop.cfg;

import ir.piana.business.multishop.common.data.cache.AppDataCache;
import ir.piana.business.multishop.common.data.cache.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {
    @Autowired
    private StaticResourcePropertiesModel staticResourceProperties;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private AppDataCache appDataCache;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public ResourceHandlerRegistryProvider resourceHandlerRegistryProvider = new ResourceHandlerRegistryProvider();

    @Bean
    public ResourceHandlerRegistryProvider getResourceHandlerRegistryProvider() {
        return resourceHandlerRegistryProvider;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        resourceHandlerRegistryProvider.setResourceHandlerRegistry(registry);
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache("spring-resource-chain-cache",
                new ConcurrentHashMap<>(), true);
        staticResourceProperties.getPaths().forEach((k, v) -> {
            registry.addResourceHandler(k)
                    .addResourceLocations(v.toArray(new String[0]))
                    .setCachePeriod(3600)
                    .resourceChain(false)
                    .addResolver(new PianaCachingResourceResolver(concurrentMapCache))
                    .addResolver(new SiteResourceResolver(appDataCache));

//                    .addResourceLocations("classpath:/static/", "file:///c:/upload-dir/");
        });
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowCredentials(true)
                .allowCredentials(false)
                .allowedOrigins("*");
//                .allowedOrigins("https://piana.ir", "https://localhost");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/*")
//                .allowedOrigins("https://piana.ir")
//                .allowedMethods("GET", "POST", "OPTIONS", "PUT")
//                .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
//                        "Host",
//                        "Access-Control-Request-Headers")
////                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//                .allowCredentials(true).maxAge(3600);
//    }
}
