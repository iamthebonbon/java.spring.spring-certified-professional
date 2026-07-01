package com.example.scp.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebClient {

    private final RestTemplate template;

    public WebClient(RestTemplateBuilder restTemplateBuilder) {
        this.template = restTemplateBuilder.build();
    }

    public void test() {
        template.getForObject("https://bonbon.local", Void.class);
    }

}
