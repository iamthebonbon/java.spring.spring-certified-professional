package com.example.scp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
        var response = testRestTemplate.getForEntity(
                URI.create("/main"),
                Void.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

}
