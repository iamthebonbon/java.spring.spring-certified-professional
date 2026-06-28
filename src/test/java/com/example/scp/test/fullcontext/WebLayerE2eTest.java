package com.example.scp.test.fullcontext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

class WebLayerE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalManagementPort
    private int managementPort;

    @Test
    void testOne() {
        var response = testRestTemplate.getForEntity(
                URI.create("/bonbon"),
                String.class
        );
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());
    }

    @Test
    void testHealth() {
        RestTemplate restTemplate = testRestTemplate.getRestTemplate();
        var response = restTemplate.getForEntity(
                String.format("http://localhost:%s/actuator/health", managementPort),
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertTrue(response.getBody().contains("UP"));
    }

    @Test
    void testInfo() {
        var response = testRestTemplate.getForEntity(
                String.format("http://localhost:%s/actuator/info", managementPort),
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

}
