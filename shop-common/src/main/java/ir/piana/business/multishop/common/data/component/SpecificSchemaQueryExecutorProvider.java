package ir.piana.business.multishop.common.data.component;

import com.zaxxer.hikari.HikariDataSource;
import ir.piana.business.multishop.common.data.util.SpecificSchemaQueryExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.Map;

@Service("SpecificSchemaQueryExecutorProvider")
@DependsOn("dataSourceService")
@Slf4j
public class SpecificSchemaQueryExecutorProvider {
    @Autowired
    @Qualifier("dataSources")
    private Map<String, HikariDataSource> datasources;

    @PostConstruct
    public void init() {
      log.info("SpecificSchemaQueryExecutorProvider => initialized");
    }

    public SpecificSchemaQueryExecutor getSpecificSchemaQueryExecutor(String tenantId) {
        return new SpecificSchemaQueryExecutor(datasources.get(tenantId));
    }

    public void executeOnAllDataSources(String query) {
        for(String key : datasources.keySet()) {
            try {
                new SpecificSchemaQueryExecutor(datasources.get(key)).execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
