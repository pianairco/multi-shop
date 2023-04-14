package ir.piana.business.multishop.module.auth.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;

public interface GoogleUserRepository extends JpaRepository<GoogleUserEntity, Long> {
    GoogleUserEntity findByEmail(String email);
}
