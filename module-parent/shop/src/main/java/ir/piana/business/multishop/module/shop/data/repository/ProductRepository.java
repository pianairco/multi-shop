package ir.piana.business.multishop.module.shop.data.repository;

import ir.piana.business.multishop.module.shop.data.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByRegistrarSiteId(long id);
    List<ProductEntity> findAllByRegistrarSiteIdAndPianaCategoryId(long siteId, long categoryId);
    ProductEntity findByRegistrarSiteIdAndId(long siteId, long id);
    void deleteByRegistrarSiteIdAndId(long siteId, long id);
}
