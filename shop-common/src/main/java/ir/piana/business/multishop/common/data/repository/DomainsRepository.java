package ir.piana.business.multishop.common.data.repository;

import ir.piana.business.multishop.common.data.entity.DomainsEntity;
import ir.piana.business.multishop.common.data.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DomainsRepository extends JpaRepository<DomainsEntity, Long> {
}
