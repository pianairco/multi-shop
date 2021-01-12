package ir.piana.business.multishop.data.repository;

import ir.piana.business.multishop.data.entity.GoogleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleUserRepository extends JpaRepository<GoogleUserEntity, Long> {
    GoogleUserEntity findByEmail(String email);
}
