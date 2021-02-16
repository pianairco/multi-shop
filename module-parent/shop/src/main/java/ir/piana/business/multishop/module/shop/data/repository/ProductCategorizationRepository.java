package ir.piana.business.multishop.module.shop.data.repository;

import ir.piana.business.multishop.module.shop.data.entity.ProductCategorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategorizationRepository extends JpaRepository<ProductCategorizationEntity, Long> {
    List<ProductCategorizationEntity> findAllBySiteId(long id);
}
