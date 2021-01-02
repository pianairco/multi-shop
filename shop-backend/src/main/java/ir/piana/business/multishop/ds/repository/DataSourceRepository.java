package ir.piana.business.multishop.ds.repository;

import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceRepository extends JpaRepository<DataSourceEntity, Long> {
}
