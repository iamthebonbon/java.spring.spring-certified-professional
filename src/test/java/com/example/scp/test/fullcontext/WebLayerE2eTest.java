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
class WebLayerE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalManagementPort
    private int managementPort;

    @Test
    @Order(100)
    void getBonbonTest() {
        var response = testRestTemplate
                .getForEntity(
                        URI.create("/bonbon"),
                        String.class
                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());
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

        String status = root.get("status").asText();
        String dbStatus = root.at("/components/db/status").asText();
        String database = root.at("/components/db/details/database").asText();

        Assertions.assertEquals("DOWN", status);
        Assertions.assertEquals("", dbStatus);
        Assertions.assertEquals("", database);
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

        String status = root.get("status").asText();
        String dbStatus = root.at("/components/db/status").asText();
        String database = root.at("/components/db/details/database").asText();

        Assertions.assertEquals("DOWN", status);
        Assertions.assertEquals("UP", dbStatus);
        Assertions.assertEquals("PostgreSQL", database);
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

        String one = root.at("/bonbon/one").asText();
        String two = root.at("/bonbon/two").asText();

        Assertions.assertEquals("Marmalade", one);
        Assertions.assertEquals("Marshmallow", two);
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
