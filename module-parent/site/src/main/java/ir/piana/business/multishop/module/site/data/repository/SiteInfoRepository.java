package ir.piana.business.multishop.module.site.data.repository;

import ir.piana.business.multishop.module.site.data.entity.SiteInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteInfoRepository
        extends JpaRepository<SiteInfoEntity, Long> {
    SiteInfoEntity findByTenantId(String tenantId);
}
