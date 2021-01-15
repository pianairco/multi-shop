package ir.piana.business.multishop.module.auth.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AuthInitializer {
    @PostConstruct
    public void init() {
        AuthInitializer.class.getResourceAsStream("/auth.sql");
    }
}
