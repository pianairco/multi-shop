package ir.piana.business.multishop.module.auth.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AuthModuleInitializer {
    @PostConstruct
    public void init() {
        AuthModuleInitializer.class.getResourceAsStream("/auth.sql");
    }
}
