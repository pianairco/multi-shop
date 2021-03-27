package ir.piana.business.multishop.common.data.repository;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteRepository extends JpaRepository<SiteEntity, Long> {
    SiteEntity findByTenantId(String tenant);
    List<SiteEntity> findAllByAgentId(Long agentId);
    List<SiteEntity> findAllByCategory(Long category);
    @Query(value = "SELECT * FROM SITE s WHERE s.category between :start and :end", nativeQuery = true)
    List<SiteEntity> findByCategory(@Param("start") long start, @Param("end") long end);
}
