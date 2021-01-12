package ir.piana.business.multishop.module.bill.initializr;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class BillModuleInitializer {

    @PostConstruct
    public void init() {
        BillModuleInitializer.class.getResourceAsStream("/bill-system.sql");
    }
}
