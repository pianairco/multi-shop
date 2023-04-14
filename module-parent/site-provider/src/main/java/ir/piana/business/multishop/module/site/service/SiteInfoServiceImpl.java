package ir.piana.business.multishop.module.site.service;

import ir.piana.business.multishop.common.data.cache.AppDataCache;
import ir.piana.business.multishop.module.site.data.entity.SiteInfoEntity;
import ir.piana.business.multishop.module.site.data.repository.SiteInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SiteInfoServiceImpl implements SiteInfoService {
    @Autowired
    private SiteInfoRepository siteInfoRepository;

    @Autowired
    private AppDataCache appDataCache;

    @Override
    public SiteInfoEntity getSiteInfoEntity(HttpServletRequest request) {
        String host = (String) request.getAttribute("host");
        String tenant = (String) request.getAttribute("tenant");
        if(!appDataCache.getDomain().equalsIgnoreCase(host)) {
            return siteInfoRepository.findByTenantId(tenant);
        }
        return null;
    }
}
