package ir.piana.business.multishop.module.site.data.repository;

import ir.piana.business.multishop.module.site.data.entity.BayaCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BayaCategoryRepository
        extends JpaRepository<BayaCategoryEntity, Long> {
}
