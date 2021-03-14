package ir.piana.business.multishop.common.data.repository;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteRepository extends JpaRepository<SiteEntity, Long> {
    SiteEntity findByTenantId(String tenant);
    List<SiteEntity> findAllByAgentId(Long agentId);
    List<SiteEntity> findAllByCategoryId(Long categoryId);

}
