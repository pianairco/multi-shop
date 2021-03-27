package ir.piana.business.multishop.module.zarinpalclient.initializr;

import ir.piana.business.multishop.common.BaseInitializer;
import ir.piana.business.multishop.common.data.component.SpecificSchemaQueryExecutorProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Component("ZarinpalModuleInitializer")
@DependsOn("SpecificSchemaQueryExecutorProvider")
@Slf4j
public class ZarinpalInitializer extends BaseInitializer {
    @Autowired
    public void setQueryExecutorProvider(SpecificSchemaQueryExecutorProvider executorProvider) {
        this.queryExecutorProvider = executorProvider;
    }

    @PostConstruct
    public void init() {
        initData();
        log.info("ZarinpalModuleInitializer => initialized");
    }

    @Override
    public InputStream getSupportSql() {
        return null;
    }

    @Override
    public InputStream getAllSchemaSql() {
        return ZarinpalInitializer.class.getResourceAsStream("/zarinpal.sql");
    }
}
