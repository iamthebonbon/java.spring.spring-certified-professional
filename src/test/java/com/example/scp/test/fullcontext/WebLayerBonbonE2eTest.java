package com.example.scp.test.fullcontext;

import com.example.scp.component.BonbonComponent;
import com.example.scp.controller.BonbonController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.net.URI;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebLayerBonbonE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockitoSpyBean
    private BonbonComponent component;

    @Test
    @Order(100)
    void bonbonTest() {
        var response = testRestTemplate
                .withBasicAuth("bonbon", "bonbon")
                .getForEntity(
                        URI.create("/bonbon"),
                        Void.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    @Order(100)
    void preAuthorizeForbiddenTest() {
        var response = testRestTemplate
                .withBasicAuth("bonbon", "bonbon")
                .postForEntity(
                        URI.create("/bonbon/pre-authorize"),
                        new BonbonController.BonbonResponse("deny"),
                        Void.class

                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());
        verify(component, never()).comCheck();
    }

    @Test
    @Order(100)
    void preAuthorizeOkTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .postForEntity(
                        URI.create("/bonbon/pre-authorize"),
                        new BonbonController.BonbonResponse("user"),
                        BonbonController.BonbonResponse.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertEquals("user", response.getBody().owner());
        verify(component, times(1)).comCheck();
    }

    @Test
    @Order(100)
    void preAuthorizeListForbiddenTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .exchange(
                        URI.create("/bonbon/pre-authorize-list"),
                        HttpMethod.POST,
                        new HttpEntity<>(List.of(
                                new BonbonController.BonbonResponse("user"),
                                new BonbonController.BonbonResponse("deny")
                        )),
                        String.class
                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());
        verify(component, never()).comCheck();
    }

    @Test
    @Order(100)
    void preAuthorizeListOkTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .exchange(
                        URI.create("/bonbon/pre-authorize-list"),
                        HttpMethod.POST,
                        new HttpEntity<>(List.of(
                                new BonbonController.BonbonResponse("user"),
                                new BonbonController.BonbonResponse("user")
                        )),
                        String.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        verify(component, times(1)).comCheck();
    }

    @Test
    @Order(100)
    void postAuthorizeForbiddenTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .postForEntity(
                        URI.create("/bonbon/post-authorize"),
                        new BonbonController.BonbonResponse("deny"),
                        BonbonController.BonbonResponse.class
                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());
        verify(component, times(1)).comCheck();
    }

    @Test
    @Order(100)
    void postAuthorizeOkTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .postForEntity(
                        URI.create("/bonbon/post-authorize"),
                        new BonbonController.BonbonResponse("user"),
                        BonbonController.BonbonResponse.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        verify(component, times(1)).comCheck();
    }

    @Test
    @Order(100)
    void postAuthorizeListForbiddenTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .exchange(
                        URI.create("/bonbon/post-authorize-list"),
                        HttpMethod.POST,
                        new HttpEntity<>(List.of(
                                new BonbonController.BonbonResponse("user"),
                                new BonbonController.BonbonResponse("deny")
                        )),
                        String.class
                );
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());
        verify(component, times(1)).comCheck();
    }

    @Test
    @Order(100)
    void postAuthorizeListOkTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .exchange(
                        URI.create("/bonbon/post-authorize-list"),
                        HttpMethod.POST,
                        new HttpEntity<>(List.of(
                                new BonbonController.BonbonResponse("user"),
                                new BonbonController.BonbonResponse("user")
                        )),
                        String.class
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        verify(component, times(1)).comCheck();
    }

    @Test
    @Order(100)
    void preFilterTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .exchange(
                        URI.create("/bonbon/pre-filter"),
                        HttpMethod.POST,
                        new HttpEntity<>(List.of(
                                new BonbonController.BonbonResponse("user"),
                                new BonbonController.BonbonResponse("deny")
                        )),
                        new ParameterizedTypeReference<List<BonbonController.BonbonResponse>>() {
                        }
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertEquals(1, response.getBody().size());
        verify(component, times(1)).comCheck();
    }

    @Test
    @Order(100)
    void postFilterTest() {
        var response = testRestTemplate
                .withBasicAuth("user", "user")
                .exchange(
                        URI.create("/bonbon/post-filter"),
                        HttpMethod.POST,
                        new HttpEntity<>(List.of(
                                new BonbonController.BonbonResponse("user"),
                                new BonbonController.BonbonResponse("deny")
                        )),
                        new ParameterizedTypeReference<List<BonbonController.BonbonResponse>>() {
                        }
                );
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertEquals(1, response.getBody().size());
        verify(component, times(1)).comCheck();
    }


}
