package io.will.springbootv4poc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class ExternalQuickTestApisControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RestTestClient restTestClient;

    /*@TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public PingClient pingClient() {
            return () -> "pong from external";
        }
    }*/

    @Test
    void testPingExternal_shouldReturnExternalApiResponse() {
        restTestClient.get()
                .uri("http://localhost:" + port + "/api/v1/external/ping")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(result -> {
                    String response = result.getResponseBody();
                    assertNotNull(response);
                    assertTrue(response.contains("PONG"));
                });
    }
}
