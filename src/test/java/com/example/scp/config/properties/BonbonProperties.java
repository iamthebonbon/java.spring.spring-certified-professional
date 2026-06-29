package com.example.scp.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bonbon")
public record BonbonProperties(String one, String two, String three, String custom) {

}
