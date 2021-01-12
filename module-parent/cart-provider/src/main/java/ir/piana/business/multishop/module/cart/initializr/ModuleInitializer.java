package ir.piana.business.multishop.module.cart.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ModuleInitializer {

    @PostConstruct
    public void init() {
        ModuleInitializer.class.getResourceAsStream("/zarinpal_client.sql");
    }
}
