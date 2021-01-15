package ir.piana.business.multishop.module.bill.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class BillInitializer {

    @PostConstruct
    public void init() {
        BillInitializer.class.getResourceAsStream("/bill.sql");
    }
}
