package ir.piana.business.multishop.common.data.repository;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<SiteEntity, Long> {
}
