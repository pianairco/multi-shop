package ir.piana.business.multishop.module.delivery.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DeliveryInitializer {

    @PostConstruct
    public void init() {
        DeliveryInitializer.class.getResourceAsStream("/delivery.sql");
    }
}
