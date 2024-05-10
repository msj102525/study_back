package org.ict.test_security.security.jwt.model.service;

import lombok.extern.slf4j.Slf4j;
import org.ict.test_security.security.jwt.jpa.entity.RefreshToken;
import org.ict.test_security.security.jwt.jpa.repository.RefreshRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class RefreshService {
    private RefreshRepository refreshRepository;

    Optional<RefreshToken> findByUserId(String userId) {


        return refreshRepository.findByUserId(userId);
    }

    public void deleteByRefresh(String tokenValue) {
        refreshRepository.deleteByTokenValue(tokenValue);
    }

}
