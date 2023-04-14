package ir.piana.business.multishop.common.data.repository;

import ir.piana.business.multishop.common.data.entity.AppInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppInfoRepository extends JpaRepository<AppInfoEntity, Long> {
}
