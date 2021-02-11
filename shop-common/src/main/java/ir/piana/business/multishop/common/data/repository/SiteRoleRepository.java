package ir.piana.business.multishop.common.data.repository;

import ir.piana.business.multishop.common.data.entity.AgentEntity;
import ir.piana.business.multishop.common.data.entity.SiteRoleEntity;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;

public interface SiteRoleRepository extends JpaRepository<SiteRoleEntity, Long> {
}
