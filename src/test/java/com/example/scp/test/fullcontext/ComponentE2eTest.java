package com.example.scp.test.fullcontext;

import com.example.scp.component.BonbonComponent;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.http.HttpStatus;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComponentE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private BonbonComponent bonbonComponent;
    @Autowired
    private Counter counter;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalManagementPort
    private int managementPort;

    @Test
    @Order(100)
    void test() {
        Assertions.assertEquals("Iam test Houston", bonbonComponent.comCheck());
        Assertions.assertEquals("Iam test Houston", bonbonComponent.comCheck());
        Assertions.assertEquals("Iam test Houston", bonbonComponent.comCheck());
        Assertions.assertEquals(3.0, counter.count());
    }

    @Test
    @Order(200)
    void actuatorMetricsHttpServerRequestsTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .getForEntity(
                        String.format("http://localhost:%s/management/metrics/bonbon-counter", managementPort),
                        String.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

}
