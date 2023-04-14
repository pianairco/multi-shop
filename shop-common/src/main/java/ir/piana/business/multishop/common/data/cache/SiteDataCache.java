package ir.piana.business.multishop.common.data.cache;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.data.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SiteDataCache {
    @Autowired
    private CacheLock cacheLock;

    @Autowired
    private SiteRepository siteRepository;

    Map<String, SiteEntity> siteMap = null;
    Map<String, SiteEntity> unmodifiableMap = null;

    private void run() {
        List<SiteEntity> all = siteRepository.findAll();
        siteMap = all.stream().collect(
                Collectors.toMap(siteEntity -> siteEntity.getTenantId(), siteEntity -> siteEntity));
        unmodifiableMap = Collections.unmodifiableMap(siteMap);
    }

    void refresh() {
        cacheLock.doSynchronized(5, true, () -> this.run());
    }

    @PostConstruct
    public void init() {
        refresh();
    }

    public Map<String, SiteEntity> getSiteMap() {
        return unmodifiableMap;
    }

    public SiteEntity getSiteEntity(String tenantId) {
        return siteMap.containsKey(tenantId) ? siteMap.get(tenantId) : null;
    }

    public boolean isActive(String tenantId) {
        return siteMap.containsKey(tenantId) ? siteMap.get(tenantId).isActive() : false;
    }

    @Transactional
    boolean addAndActivate(SiteEntity siteEntity) {
        if(siteMap.containsKey(siteEntity.getTenantId()))
            return false;
        siteMap.put(siteEntity.getTenantId(), siteEntity);
        return activate(siteEntity.getTenantId());
    }

    @Transactional
    boolean activate(String tenantId) {
        try {
            SiteEntity siteEntity = siteMap.get(tenantId);
            if(siteEntity == null)
                return false;
            else if(siteEntity.isActive())
                return true;
            siteEntity.setActive(true);
            siteRepository.save(siteEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    SiteEntity remove(SiteEntity siteEntity) {
        if(!siteMap.containsKey(siteEntity.getTenantId()))
            return null;
        return siteMap.remove(siteEntity.getTenantId());
    }


    @Transactional
    boolean deactivate(String tenantId) {
        try {
            SiteEntity siteEntity = siteMap.get(tenantId);
            if(siteEntity == null)
                return false;
            else if(!siteEntity.isActive())
                return true;
            siteEntity.setActive(false);
            siteRepository.save(siteEntity);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
