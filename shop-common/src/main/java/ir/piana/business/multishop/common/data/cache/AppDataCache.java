package ir.piana.business.multishop.common.data.cache;

import ir.piana.business.multishop.common.data.entity.AppInfoEntity;
import ir.piana.business.multishop.common.data.repository.AppInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AppDataCache {
    @Autowired
    private CacheLock cacheLock;

    @Autowired
    private AppInfoRepository appInfoRepository;

    Map<String, AppInfoEntity> appInfoMap = null;

    private void run() {
        List<AppInfoEntity> all = appInfoRepository.findAll();
        appInfoMap = Collections.unmodifiableMap(all.stream().collect(
                Collectors.toMap(appInfoEntity -> appInfoEntity.getAppParam(), appInfoEntity -> appInfoEntity)));
    }

    void refresh() {
        cacheLock.doSynchronized(5, true, () -> this.run());
    }

    @PostConstruct
    public void init() {
        refresh();
    }

    public Map<String, AppInfoEntity> getAppInfoMap() {
        return appInfoMap;
    }

    public String getParamValue(String appParam) {
        return appInfoMap.containsKey(appParam.toUpperCase()) ?
                appInfoMap.get(appParam.toUpperCase()).getParamValue() : null;
    }

    public String getDomain() {
        return appInfoMap.get("APP_DOMAIN").getParamValue();
    }
}
