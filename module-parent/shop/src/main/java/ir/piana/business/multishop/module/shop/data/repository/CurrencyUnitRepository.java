package ir.piana.business.multishop.module.shop.data.repository;

import ir.piana.business.multishop.module.shop.data.entity.CurrencyUnitEntity;
import ir.piana.business.multishop.module.shop.data.entity.MeasurementUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyUnitRepository extends JpaRepository<CurrencyUnitEntity, Long> {
//    List<MeasurementUnitEntity> getAll();
    /*@Query(value = "SELECT p.* FROM product p where p.registrar_site_ID = :siteId and p.PIANA_CATEGORY_ID >= :fromCategoryId and p.PIANA_CATEGORY_ID < :toCategoryId order by id asc", nativeQuery = true)
    List<ProductEntity> findAllByRegistrarSiteIdAndPianaCategoryId(
            @Param("siteId") long siteId, @Param("fromCategoryId") long fromCategoryId, @Param("toCategoryId") long toCategoryId);*/
}
