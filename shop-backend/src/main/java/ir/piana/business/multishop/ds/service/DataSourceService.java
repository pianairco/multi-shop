package ir.piana.business.multishop.ds.service;


import com.zaxxer.hikari.HikariDataSource;
import ir.piana.business.multishop.ds.config.SpecificSchemaQueryExecutor;
import ir.piana.business.multishop.ds.config.TenantContext;
import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import ir.piana.business.multishop.ds.repository.DataSourceRepository;
import ir.piana.business.multishop.exceptions.ChangeStatusException;
import ir.piana.business.multishop.exceptions.ErrorModel;
import ir.piana.business.multishop.exceptions.RefreshDatasourceException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("dataSourceService")
@DependsOn({ "supportExecutor",
        "multiShopDataSources",
        "failedDataSources",
        "entityManagerFactoryBean", "entityManagerFactory", "txManager"})
@Transactional(propagation = Propagation.REQUIRED)
public class DataSourceService {
    private HikariDataSource supportDatasource;

    @Autowired
    @Qualifier("supportExecutor")
    private SpecificSchemaQueryExecutor supportExecutor;

    @Autowired
    @Qualifier("multiShopDataSources")
    private List<DataSourceEntity> multiShopDataSources;


    @Autowired
    @Qualifier("failedDataSources")
    private Map<String, DataSourceEntity> failedDataSources;

    @Autowired
    @Qualifier("dataSources")
    private Map<String, DataSource> dataSourceMap;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    private boolean isLock;

    public DataSourceService() {

    }

    @PostConstruct
    public void init() throws Exception {
        this.supportDatasource = supportExecutor.getDatasource();
        refreshDataSources();
    }

    private synchronized List<DataSourceEntity> refreshMultiShopDataSources()
            throws SQLException {
        multiShopDataSources.clear();
        List<DataSourceEntity> all = dataSourceRepository.findAll();

        multiShopDataSources.addAll(all);

//        List<Map<String, Object>> mapList = supportExecutor.queryListOfMap(
//                "select TENANT_ID, DB_NAME, SCHEMA_NAME, DB_PASSWORD, MAX_PULL_SIZE, IS_DEFAULT, IS_ACTIVE from sejam_datasources");
        return multiShopDataSources;
    }

    public synchronized HikariDataSource createHikariDS(
            DataSourceEntity dataSourceEntity)
            throws SQLException, IOException {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(dataSourceEntity.getDriver());
        ds.setJdbcUrl(dataSourceEntity.getUrl());
        ds.setUsername(dataSourceEntity.getUsername());
        ds.setPassword(dataSourceEntity.getPassword());
        ds.setPoolName("Pool-" + dataSourceEntity.getSchemaName() + "-" + dataSourceEntity.getTenantId());
        ds.setMaximumPoolSize(dataSourceEntity.getMaxPullSize());
        ds.setConnectionTimeout(5000);
        ds.setIdleTimeout(5000);
        ds.setInitializationFailTimeout(5000);
        SpecificSchemaQueryExecutor specificSchemaQueryExecutor = new SpecificSchemaQueryExecutor(ds);
        specificSchemaQueryExecutor.queryInt("select 1 from dual");
        if(dataSourceEntity.getScriptPath() != null) {
            File file = ResourceUtils.getFile(dataSourceEntity.getScriptPath());
            InputStream inputStream = new FileInputStream(file);
            String[] split = IOUtils.toString(inputStream).split(";");
            for (String script : split) {
                try {
                    specificSchemaQueryExecutor.execute(script);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            inputStream.close();
        }
        return ds;
    }

    public synchronized void datasourceActivation(DataSourceEntity dataSourceEntity, boolean isThrows) {
        try {
            HikariDataSource ds = createHikariDS(dataSourceEntity);
            dataSourceMap.put(dataSourceEntity.getTenantId(), ds);

            failedDataSources.remove(dataSourceEntity);
            dataSourceEntity.setActive(true);
            dataSourceRepository.save(dataSourceEntity);
        } catch (Exception e) {
            failedDataSources.put(dataSourceEntity.getTenantId(), dataSourceEntity);
            if(isThrows)
                throw new ChangeStatusException(dataSourceEntity);
        }
    }

    public synchronized void datasourceDeactivation(DataSourceEntity sejamDataSourceEntity) {
        sejamDataSourceEntity.setActive(false);
        dataSourceRepository.save(sejamDataSourceEntity);
//            activeDataSources.remove(sejamDataSourceEntity);
//            deactivateDataSources.add(sejamDataSourceEntity);
        HikariDataSource hikariDataSource = (HikariDataSource) dataSourceMap
                .remove(sejamDataSourceEntity.getTenantId());
        hikariDataSource.close();
    }

    public synchronized DataSourceEntity insertNewDatasource(DataSourceEntity sejamDataSourceEntity)
            throws Exception {
        dataSourceRepository.save(sejamDataSourceEntity);
        multiShopDataSources.add(sejamDataSourceEntity);
        if(sejamDataSourceEntity.isActive())
            datasourceActivation(sejamDataSourceEntity, true);

        return sejamDataSourceEntity;
    }

    public synchronized Map<String, DataSource> refreshDataSources()
            throws Exception {
        if(isLock)
            throw new RefreshDatasourceException(ErrorModel.builder()
                    .message("refresh datasources is in progress").build());
        isLock = true;
        while (TenantContext.getCounter() > 0) {
            Thread.sleep(1000);
        }

        refreshMultiShopDataSources();

        dataSourceMap.forEach((k, dataSource) -> {
            if(!k.equalsIgnoreCase("support"))
                ((HikariDataSource)dataSource).close();
        });
        dataSourceMap.clear();
        failedDataSources.clear();
        dataSourceMap.put("support", supportDatasource);
//        Map<String, DataSource> result = new HashMap<>();
        for (DataSourceEntity dataSourceEntity : multiShopDataSources.stream()
                .filter(e -> e.isActive()).collect(Collectors.toList())) {
//                .stream().filter(e -> e.getSchemaName().equalsIgnoreCase("saf5")).collect(Collectors.toList())) {
            datasourceActivation(dataSourceEntity, false);
        }
        isLock = false;
        return dataSourceMap;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DataSourceEntity changeStatus(String tenantId) {
        Optional<DataSourceEntity> first = multiShopDataSources.stream()
                .filter(e -> e.getTenantId().equalsIgnoreCase(tenantId)).findFirst();
        DataSourceEntity sejamDataSourceEntity = first.orElseThrow(
                () -> new ChangeStatusException(null));
        if (sejamDataSourceEntity.isActive()) {
            datasourceDeactivation(sejamDataSourceEntity);
        } else {
            datasourceActivation(sejamDataSourceEntity, true);
        }
        return sejamDataSourceEntity;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DataSourceEntity tryConnect(String tenantId) {
        DataSourceEntity sejamDataSourceEntity = failedDataSources.get(tenantId);
        if(sejamDataSourceEntity != null) {
            if(sejamDataSourceEntity.isActive()) {
                datasourceActivation(sejamDataSourceEntity, true);
            }
        }
        return sejamDataSourceEntity;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }
}
