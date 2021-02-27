package ir.piana.business.multishop.module.shop.data.repository;

import ir.piana.business.multishop.module.shop.data.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllBySiteId(long id);
    List<ProductEntity> findAllBySiteIdAndCategoryId(long siteId, long categoryId);
    ProductEntity findBySiteIdAndId(long siteId, long id);
    void deleteBySiteIdAndId(long siteId, long id);
}
