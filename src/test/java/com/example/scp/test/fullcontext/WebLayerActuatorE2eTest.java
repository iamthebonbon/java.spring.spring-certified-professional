package com.example.scp.test.fullcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebLayerActuatorE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalManagementPort
    private int managementPort;

    /**
     * init metrics: http.server.requests
     */
    @Test
    @Order(100)
    void bonbonTest() {
        var response = testRestTemplate
                .getForEntity(
                        URI.create("/bonbon"),
                        String.class
                );
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
    }

    @Test
    @Order(200)
    void actuatorHealthTest() throws JsonProcessingException {
        RestTemplate restTemplate = testRestTemplate.getRestTemplate();
        var response = restTemplate.getForEntity(
                String.format("http://localhost:%s/management/status", managementPort),
                String.class
        );
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), response.getStatusCode().value());

        JsonNode root = getJsonNode(response);
        Assertions.assertEquals("DOWN", root.get("status").asText());
        Assertions.assertEquals("", root.at("/components/db/status").asText());
        Assertions.assertEquals("", root.at("/components/db/details/database").asText());
    }

    @Test
    @Order(200)
    void actuatorHealthWhenAuthorizedTest() throws JsonProcessingException {
        RestTemplate restTemplate = testRestTemplate
                .withBasicAuth("user", "user")
                .getRestTemplate();
        var response = restTemplate.getForEntity(
                String.format("http://localhost:%s/management/status", managementPort),
                String.class
        );
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), response.getStatusCode().value());

        JsonNode root = getJsonNode(response);
        Assertions.assertEquals("DOWN", root.get("status").asText());
        Assertions.assertEquals("UP", root.at("/components/db/status").asText());
        Assertions.assertEquals("PostgreSQL", root.at("/components/db/details/database").asText());
    }

    @Test
    @Order(200)
    void actuatorHealthDefaultTest() throws JsonProcessingException {
        RestTemplate restTemplate = testRestTemplate
                .withBasicAuth("user", "user")
                .getRestTemplate();
        var response = restTemplate.getForEntity(
                String.format("http://localhost:%s/management/status/default", managementPort),
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());

        JsonNode root = getJsonNode(response);
        Assertions.assertEquals("UP", root.get("status").asText());
        Assertions.assertEquals("UP", root.at("/components/db/status").asText());
        Assertions.assertEquals("PostgreSQL", root.at("/components/db/details/database").asText());
    }

    @Test
    @Order(200)
    void actuatorHealthBonbonTest() throws JsonProcessingException {
        RestTemplate restTemplate = testRestTemplate
                .withBasicAuth("user", "user")
                .getRestTemplate();
        var response = restTemplate.getForEntity(
                String.format("http://localhost:%s/management/status/external", managementPort),
                String.class
        );
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), response.getStatusCode().value());
        Assertions.assertEquals("{\"status\":\"DOWN\",\"components\":{\"bonbon\":{\"status\":\"DOWN\",\"details\":{\"houston\":\"com, check\"}}}}", response.getBody());
    }

    @Test
    @Order(200)
    void actuatorBonbonTest() {
        RestTemplate restTemplate = testRestTemplate
                .withBasicAuth("user", "user")
                .getRestTemplate();
        var response = restTemplate.getForEntity(
                String.format("http://localhost:%s/management/bonbon", managementPort),
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertEquals("Halo, houston", response.getBody());
    }

    @Test
    @Order(200)
    void actuatorInfoTest() throws JsonProcessingException {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .getForEntity(
                        String.format("http://localhost:%s/management/info", managementPort),
                        String.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());

        JsonNode root = getJsonNode(response);
        Assertions.assertEquals("Marmalade", root.at("/bonbon/one").asText());
        Assertions.assertEquals("Marshmallow", root.at("/bonbon/two").asText());
    }

    @Test
    @Order(200)
    void actuatorMetricsTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .getForEntity(
                        String.format("http://localhost:%s/management/metrics", managementPort),
                        String.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    @Order(200)
    void actuatorMetricsHttpServerRequestsTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .getForEntity(
                        String.format("http://localhost:%s/management/metrics/http.server.requests", managementPort),
                        String.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    private static JsonNode getJsonNode(ResponseEntity<String> response) throws JsonProcessingException {
        return new ObjectMapper().readTree(response.getBody());
    }

}
