package com.example.scp.test.fullcontext;

import com.example.scp.component.BonbonComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.net.URI;

class WebLayerMainE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockitoSpyBean
    private BonbonComponent component;

    @Test
    void bonbonTest() {
        var response = testRestTemplate
                .getForEntity(
                        URI.create("/"),
                        Void.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

}
