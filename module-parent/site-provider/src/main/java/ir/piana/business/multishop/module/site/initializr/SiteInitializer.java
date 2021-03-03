package ir.piana.business.multishop.module.site.initializr;

import ir.piana.business.multishop.common.CommonInitializer;
import ir.piana.business.multishop.common.data.component.SpecificSchemaQueryExecutorProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Configuration
@Slf4j
@DependsOn("SpecificSchemaQueryExecutorProvider")
public class SiteInitializer extends CommonInitializer {

    @Autowired
    public void setQueryExecutorProvider(SpecificSchemaQueryExecutorProvider executorProvider) {
        this.queryExecutorProvider = executorProvider;
    }

    @PostConstruct
    public void init() {
        initData();
        log.info("AuthModuleInitializer => initialized");
    }

    @Override
    public InputStream getSupportSql() {
        return SiteInitializer.class.getResourceAsStream("/site.sql");
    }

    @Override
    public InputStream getAllSchemaSql() {
        return null;
//        return AuthInitializer.class.getResourceAsStream("/site.sql");
    }
}
