package ir.piana.business.multishop.common.data.cache;


import com.zaxxer.hikari.HikariDataSource;
import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.data.repository.SiteRepository;
import ir.piana.business.multishop.common.data.util.SpecificSchemaQueryExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("dataSourceService")
@DependsOn({ "supportExecutor",
        "entityManagerFactoryBean", "entityManagerFactory", "txManager"})
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class DataSourceService {
    private HikariDataSource supportDatasource;

    @Autowired
    @Qualifier("supportExecutor")
    private SpecificSchemaQueryExecutor supportExecutor;

    @Autowired
    @Qualifier("dataSources")
    private Map<String, HikariDataSource> dataSourceMap;

    @Autowired
    private AppDataCache appDataCache;

    @Autowired
    private SiteDataCache siteDataCache;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private CacheLock cacheLock;

    public DataSourceService() {

    }

    @PostConstruct
    public void init() throws Exception {
        this.supportDatasource = supportExecutor.getDatasource();
        refreshDataSources();
        log.info("DataSourceService => initialized");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean checkAndAddDatasource(String tenantId) {
        if(dataSourceMap.containsKey(tenantId))
            return true;
        else if (siteDataCache.getSiteEntity(tenantId) != null &&
                siteDataCache.isActive(tenantId)) {
            dataSourceMap.put(tenantId, supportDatasource);
            return true;
        }
        return false;
    }

    @Transactional
    public void siteActivation(String tenantId) {
        cacheLock.doSynchronized(5, true, () -> {
            if(!siteDataCache.isActive(tenantId)) {
                siteDataCache.activate(tenantId);
                dataSourceMap.put(tenantId, supportDatasource);
            }
        });
    }

    @Transactional
    public void siteDeactivation(String tenantId) {
        cacheLock.doSynchronized(5, true, () -> {
            if(siteDataCache.isActive(tenantId)) {
                siteDataCache.deactivate(tenantId);
                dataSourceMap.remove(tenantId);
            }
        });
    }

    @Transactional
    public SiteEntity insertSite(SiteEntity siteEntity)
            throws Exception {
        cacheLock.doSynchronized(5, true, () -> {
            siteRepository.save(siteEntity);
            if(siteEntity.isActive())
                siteDataCache.addAndActivate(siteEntity);
        });
        return siteEntity;
    }

    public Map<String, HikariDataSource> refreshDataSources()
            throws Exception {
        try {
            TenantContext.lock();
            while (TenantContext.getCounter() > 0) {
                Thread.sleep(1000);
            }
            siteDataCache.refresh();
            dataSourceMap.clear();
            dataSourceMap.put(appDataCache.getDomain(), supportDatasource);
            return dataSourceMap;
        } catch (Exception e) {
            return null;
        } finally {
            TenantContext.unlock();
        }
    }

//    @Transactional(propagation = Propagation.REQUIRED)
//    public DataSourceEntity changeStatus(String tenantId) {
//        Optional<DataSourceEntity> first = multiShopDataSources.stream()
//                .filter(e -> e.getTenantId().equalsIgnoreCase(tenantId)).findFirst();
//        DataSourceEntity sejamDataSourceEntity = first.orElseThrow(
//                () -> new ChangeStatusException(null));
//        if (sejamDataSourceEntity.isActive()) {
//            siteDeactivation(sejamDataSourceEntity);
//        } else {
//            siteActivation(sejamDataSourceEntity, true);
//        }
//        return sejamDataSourceEntity;
//
//    }

//    @Transactional(propagation = Propagation.REQUIRED)
//    public SiteEntity tryConnect(String tenantId) {
//        SiteEntity siteEntity = siteDataCache.getSiteEntity(tenantId);
//        if(siteEntity != null) {
//            if(siteEntity.isActive()) {
//                siteActivation(siteEntity.getTenantId());
//            }
//        }
//        return siteEntity;
//    }
}
