package ir.piana.business.multishop.baya.data.repository;

import ir.piana.business.multishop.baya.data.entity.BayaCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BayaCategoryRepository
        extends JpaRepository<BayaCategoryEntity, Long> {
}
