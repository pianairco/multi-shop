package ir.piana.business.multishop.common.data.repository;

import ir.piana.business.multishop.common.data.entity.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<AgentEntity, Long> {
}
