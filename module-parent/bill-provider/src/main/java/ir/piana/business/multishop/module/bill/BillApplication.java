package ir.piana.business.multishop.module.bill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
