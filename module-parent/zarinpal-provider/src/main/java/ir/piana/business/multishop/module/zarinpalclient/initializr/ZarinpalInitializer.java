package ir.piana.business.multishop.module.zarinpalclient.initializr;

import ir.piana.business.multishop.common.data.component.SpecificSchemaQueryExecutorProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component("ZarinpalModuleInitializer")
@DependsOn("SpecificSchemaQueryExecutorProvider")
@Slf4j
public class ZarinpalInitializer {
    @Autowired
    private SpecificSchemaQueryExecutorProvider queryExecutorProvider;

    @PostConstruct
    public void init() {
        try {
            InputStream resourceAsStream = ZarinpalInitializer.class.getResourceAsStream("/zarinpal.sql");
            String[] split = new String[0];
            split = IOUtils.toString(resourceAsStream).split(";");

            for (String script : split) {
                queryExecutorProvider.executeOnAllDataSources(script);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("ZarinpalModuleInitializer => initialized");
    }
}
