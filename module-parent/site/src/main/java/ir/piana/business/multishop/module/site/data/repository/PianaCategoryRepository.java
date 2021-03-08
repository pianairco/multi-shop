package ir.piana.business.multishop.module.site.data.repository;

import ir.piana.business.multishop.module.site.data.entity.PianaCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PianaCategoryRepository
        extends JpaRepository<PianaCategoryEntity, Long> {
}
