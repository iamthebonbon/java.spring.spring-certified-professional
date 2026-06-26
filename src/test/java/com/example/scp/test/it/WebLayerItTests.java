package com.example.scp.test.it;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.net.URI;

class WebLayerItTests extends AbstractItConfiguration {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testOne() {
        var response = testRestTemplate.getForEntity(
                URI.create("/bonbon"),
                String.class
        );
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());
//        Assertions.assertTrue(response.getBody().contains("health"));
    }

    @Test
    void testHealth() {
        var response = testRestTemplate.getForEntity(
                URI.create("/actuator/health"),
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertTrue(response.getBody().contains("UP"));
    }

    @Test
    void testInfo() {
        var response = testRestTemplate.getForEntity(
                URI.create("/actuator/info"),
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

}
