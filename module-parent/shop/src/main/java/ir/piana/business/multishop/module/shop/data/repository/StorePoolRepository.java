package ir.piana.business.multishop.module.shop.data.repository;

import ir.piana.business.multishop.module.shop.data.entity.ProductEntity;
import ir.piana.business.multishop.module.shop.data.entity.StorePoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StorePoolRepository extends JpaRepository<StorePoolEntity, Long> {
    List<StorePoolEntity> findAllBySiteId(long siteId);
    @Query(value = "SELECT p.* FROM store_pool p where p.site_ID = :siteId and p.CATEGORY_ID = :categoryId order by id asc", nativeQuery = true)
    List<StorePoolEntity> findAllBySiteIdAndCategoryId(
            @Param("siteId") long siteId, @Param("categoryId") long categoryId);
}
