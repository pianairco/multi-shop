package ir.piana.business.multishop.module.cart.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ShopInitializer {

    @PostConstruct
    public void init() {
        ShopInitializer.class.getResourceAsStream("/shop.sql");
    }
}
