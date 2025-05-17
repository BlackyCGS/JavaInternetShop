package com.myshop.internetshop.classes.utilities;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TokenAutoCleanup {

    private final Logger logger = LoggerFactory.getLogger(TokenAutoCleanup.class);
    private final CleanupProcessor cleanupProcessor;
    @Autowired
    public TokenAutoCleanup(CleanupProcessor cleanupProcessor) {
        this.cleanupProcessor = cleanupProcessor;
    }

    @PostConstruct
    private void startupCleanup() {
        cleanupProcessor.cleanupTokens();
        logger.info("Token auto cleanup in startup completed.");
    }

    // Every 30 mins
    @Scheduled(fixedRate = 1800000)
    private void cleanup() {
        cleanupProcessor.cleanupTokens();
        logger.info("Scheduled token auto cleanup completed.");
    }

}
