package com.example.scp.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class BonbonHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.down()
                .withDetail("houston", "com, check")
                .build();
    }
}
