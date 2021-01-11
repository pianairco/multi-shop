package ir.piana.business.multishop.module.zarinpalclient.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ModuleInitializer {

    @PostConstruct
    public void init() {
        ModuleInitializer.class.getResourceAsStream("/zarinpal_client.sql");
    }
}
