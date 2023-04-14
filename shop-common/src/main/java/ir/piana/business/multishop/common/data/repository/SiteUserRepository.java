package ir.piana.business.multishop.common.data.repository;

import ir.piana.business.multishop.common.data.entity.SiteUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUserEntity, Long> {
}
