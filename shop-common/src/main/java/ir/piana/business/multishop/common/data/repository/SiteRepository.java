package ir.piana.business.multishop.common.data.repository;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteRepository extends JpaRepository<SiteEntity, Long> {
    @Query(value = "SELECT s.* FROM SITE s, domains d WHERE d.domain = :domain and d.site_id = s.id", nativeQuery = true)
    SiteEntity findByDomain(@Param("domain") String domain);
    List<SiteEntity> findAllByOwnerId(Long ownerId);
//    List<SiteEntity> findAllByAgentId(Long agentId);
    List<SiteEntity> findAllByCategory(Long category);
    @Query(value = "SELECT * FROM SITE s WHERE s.category between :start and :end", nativeQuery = true)
    List<SiteEntity> findByCategory(@Param("start") long start, @Param("end") long end);
    @Query(value = "SELECT s.* FROM SITE s, AGENT a WHERE a.user_id = :userId and a.site_id = s.id", nativeQuery = true)
    List<SiteEntity> findByAgentUserId(@Param("userId") long userId);
    @Query(value = "SELECT s.TENANT_ID FROM SITE s, domains d WHERE d.domain = :domain and d.site_id = s.id", nativeQuery = true)
    String getTenantId(@Param("domain") String domain);
}
