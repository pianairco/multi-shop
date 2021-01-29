package ir.piana.business.multishop.module.bill;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@SpringBootApplication
public class BillApplication {

//	@Bean
//	public ObjectMapper getObjectMapper() {
//		return new ObjectMapper();
//	}
//
//	@Bean
//	public RestTemplate getRestTemplate() {
//		return new RestTemplate();
//	}

	public static void main(String[] args) {
//		System.out.println(UUID.randomUUID().toString().length());
		SpringApplication.run(BillApplication.class, args);
	}

}
