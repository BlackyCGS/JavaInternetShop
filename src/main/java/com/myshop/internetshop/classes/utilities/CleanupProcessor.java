package com.myshop.internetshop.classes.utilities;

import com.myshop.internetshop.classes.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class CleanupProcessor {

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public CleanupProcessor(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public void cleanupTokens() {
        Date now = new Date();
        refreshTokenRepository.deleteAllByExpirationTimeBefore(now);
    }
}
