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
                .uri("http://localhost:" + port + "/api/v1/user")
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

    @Test
    void testGetUserById_shouldReturnUser() throws Exception {
        // First create a user
        User user = new User("Jane Smith", "jane.smith@example.com");
        User createdUser = restTestClient.post()
                .uri("http://localhost:" + port + "/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUser);
        Long userId = createdUser.getId();

        // Then retrieve it
        User retrievedUser = restTestClient.get()
                .uri("http://localhost:" + port + "/api/v1/user/" + userId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(retrievedUser);
        assertEquals(userId, retrievedUser.getId());
        assertEquals("Jane Smith", retrievedUser.getName());
        assertEquals("jane.smith@example.com", retrievedUser.getEmail());
    }

    @Test
    void testUpdateUser_shouldReturnUpdatedUser() throws Exception {
        // First create a user
        User user = new User("Bob Johnson", "bob.johnson@example.com");
        User createdUser = restTestClient.post()
                .uri("http://localhost:" + port + "/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUser);
        Long userId = createdUser.getId();

        // Then update it
        User updatedUserData = new User("Bob Johnson Updated", "bob.updated@example.com");
        User updatedUser = restTestClient.put()
                .uri("http://localhost:" + port + "/api/v1/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedUserData)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getId());
        assertEquals("Bob Johnson Updated", updatedUser.getName());
        assertEquals("bob.updated@example.com", updatedUser.getEmail());
    }

    @Test
    void testDeleteUser_shouldReturnNoContent() throws Exception {
        // First create a user
        User user = new User("Alice Brown", "alice.brown@example.com");
        User createdUser = restTestClient.post()
                .uri("http://localhost:" + port + "/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUser);
        Long userId = createdUser.getId();

        // Then delete it
        restTestClient.delete()
                .uri("http://localhost:" + port + "/api/v1/user/" + userId)
                .exchange()
                .expectStatus()
                .isNoContent();

        // Verify it's deleted by trying to get it
        restTestClient.get()
                .uri("http://localhost:" + port + "/api/v1/user/" + userId)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}

