package ir.piana.business.multishop.common.cfg;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ir.piana.business.multishop.common.util.LowerCaseKeyDeserializer;
import ir.piana.business.multishop.common.util.LowerCaseKeySerializer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfiguration {
//    @Bean
//	public ObjectMapper getObjectMapper() {
//		return new ObjectMapper();
//	}
//
//    @Bean
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

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
}
