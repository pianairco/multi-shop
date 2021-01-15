package ir.piana.business.multishop.module.cart.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class CartInitializer {

    @PostConstruct
    public void init() {
        CartInitializer.class.getResourceAsStream("/cart.sql");
    }
}
