package ir.piana.business.multishop.common.cfg;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class CommonConfiguration {
    @Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
