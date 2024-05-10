package org.ict.test_security.security.jwt.jpa.repository;

import org.ict.test_security.security.jwt.jpa.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByTokenValue(String tokenValue);
    Boolean existsByTokenValue(String tokenValue);
    void deleteByTokenValue(String tokenValue);
    Optional<RefreshToken> findByUserId(String id);
}



