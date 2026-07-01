package com.example.scp.test.fullcontext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

class WebLayerCorsE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalManagementPort
    private int managementPort;

    @Test
    void corsOkTest() {
        var response = testRestTemplate
                .exchange(
                        "/cors",
                        HttpMethod.OPTIONS,
                        new HttpEntity<>(
                                new LinkedMultiValueMap<>(Map.of(
                                        HttpHeaders.ORIGIN, List.of("https://bonbon.local"),
                                        HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, List.of("GET"),
                                        HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, List.of("content-type", "authorization")
                                ))
                        ),
                        Void.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    void corsDeniedTest() {
        var response = testRestTemplate
                .exchange(
                        "/no-cors",
                        HttpMethod.OPTIONS,
                        new HttpEntity<>(
                                new LinkedMultiValueMap<>(Map.of(
                                        HttpHeaders.ORIGIN, List.of("https://bonbon.local"),
                                        HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, List.of("GET"),
                                        HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, List.of("content-type", "authorization")
                                ))
                        ),
                        Void.class
                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());
    }

    @Test
    void corsOkActuatorTest() {
        RestTemplate restTemplate = testRestTemplate.getRestTemplate();
        var response = restTemplate
                .exchange(
                        String.format("http://localhost:%s/management/status", managementPort),
                        HttpMethod.OPTIONS,
                        new HttpEntity<>(
                                new LinkedMultiValueMap<>(Map.of(
                                        HttpHeaders.ORIGIN, List.of("https://bonbon.local"),
                                        HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, List.of("GET"),
                                        HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, List.of("content-type", "authorization")
                                ))
                        ),
                        Void.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    void corsDeniedActuatorTest() {
        RestTemplate restTemplate = testRestTemplate.getRestTemplate();
        var response = restTemplate
                .exchange(
                        String.format("http://localhost:%s/management/status", managementPort),
                        HttpMethod.OPTIONS,
                        new HttpEntity<>(
                                new LinkedMultiValueMap<>(Map.of(
                                        HttpHeaders.ORIGIN, List.of("https://bonbon.local"),
                                        HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, List.of("PUT"),
                                        HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, List.of("content-type", "authorization")
                                ))
                        ),
                        Void.class
                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());
    }

}
