package com.example.scp.test.slice;

import com.example.scp.client.WebClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(WebClient.class)
public class WebClientSliceTest {

    @Autowired
    MockRestServiceServer server;
    @Autowired
    WebClient weatherClient;

    @Test
    void fetchWeather_returnsExpectedData() {
        server.expect(requestTo("https://bonbon.local"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        weatherClient.test();
        server.verify();
    }

}
