package io.will.springbootv4poc.controller;

import io.will.springbootv4poc.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void testCreateUser_shouldReturnCreatedUser() throws Exception {
        User user = new User("John Doe", "john.doe@example.com");

        User createdUser = restTestClient.post()
                .uri("http://localhost:" + port + "/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals("John Doe", createdUser.getName());
        assertEquals("john.doe@example.com", createdUser.getEmail());
    }
}

