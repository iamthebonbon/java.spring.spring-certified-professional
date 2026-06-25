package com.example.scp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTests.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testOne() {
        LOGGER.info("ApplicationTests is called");
        var response = testRestTemplate.getForEntity(
                URI.create("/"),
                Void.class
        );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

}
