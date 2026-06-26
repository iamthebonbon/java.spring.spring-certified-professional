package com.example.scp.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.net.URI;

class WebLayerTests extends AbstractFullConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLayerTests.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testOne() {
        LOGGER.info("ApplicationTests is called");
        var response = testRestTemplate.getForEntity(
                URI.create("/bonbon"),
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertTrue(response.getBody().contains("health"));
    }

    @Test
    void testHealth() {
        LOGGER.info("ApplicationTests is called");
        var response = testRestTemplate.getForEntity(
                URI.create("/actuator/health"),
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertTrue(response.getBody().contains("UP"));
    }

    @Test
    void testInfo() {
        LOGGER.info("ApplicationTests is called");
        var response = testRestTemplate.getForEntity(
                URI.create("/actuator/info"),
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

}
