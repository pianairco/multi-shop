package ir.piana.business.multishop.module.order.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class OrderInitializer {

    @PostConstruct
    public void init() {
        OrderInitializer.class.getResourceAsStream("/shop.sql");
    }
}
