package com.example.scp.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface BonbonProjection {
    @Value("#{target.id + ' ' + target.candyType}")
    String getType();
}
