package ir.piana.business.multishop.module.site.data.repository;

import ir.piana.business.multishop.module.site.data.entity.PianaCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PianaCategoryRepository
        extends JpaRepository<PianaCategoryEntity, Long> {
//    List<PianaCategoryEntity> findOrderById();
    @Query(value = "SELECT * FROM PIANA_CATEGORIES p order by id asc", nativeQuery = true)
    List<PianaCategoryEntity> getAllOrderByIdAsc();
}
