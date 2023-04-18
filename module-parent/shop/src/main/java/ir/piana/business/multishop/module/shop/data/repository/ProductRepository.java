package ir.piana.business.multishop.module.shop.data.repository;

import ir.piana.business.multishop.module.shop.data.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByRegistrarSiteId(long id);
    @Query(value = "SELECT p.* FROM product p where p.registrar_site_ID = :siteId and p.PIANA_CATEGORY_ID >= :fromCategoryId and p.PIANA_CATEGORY_ID < :toCategoryId order by id asc", nativeQuery = true)
    List<ProductEntity> findAllByRegistrarSiteIdAndPianaCategoryId(
            @Param("siteId") long siteId, @Param("fromCategoryId") long fromCategoryId, @Param("toCategoryId") long toCategoryId);
    ProductEntity findByRegistrarSiteIdAndId(long siteId, long id);
    void deleteByRegistrarSiteIdAndId(long siteId, long id);
}
