package ir.piana.business.multishop.module.discount.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DiscountInitializer {

    @PostConstruct
    public void init() {
        DiscountInitializer.class.getResourceAsStream("/discount.sql");
    }
}
