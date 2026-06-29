package com.example.sibling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SiblingBonbonComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(SiblingBonbonComponent.class);

    public void run() {
        LOGGER.info("Evil sibling: {}", System.currentTimeMillis());
    }
}
