package com.example;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @LocalServerPort
    int port;

    @Autowired
    Application server;

    @BeforeEach
    void setUp() {
        server.start();
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    @DisplayName("webserver has a valid port")
    void getPort() {
        assertThat(server.getPort(), greaterThan(0));
        assertThat(server.getPort(), equalTo(port));
    }

    @Test
    @DisplayName("webserver is running")
    void isRunning() {
        assertThat(server.isRunning(), is(true));
    }

    @Test
    @DisplayName("can stop the server")
    void canStopServer() {
        server.stop();
        assertThat(server.isRunning(), is(false));
    }

    @Nested
    @DisplayName("API Endpoint Tests")
    class ApiTests {

        @Test
        @DisplayName("api - root endpoint returns Hello, World!")
        void testRootEndpoint() {
            RestClient restClient = RestClient.create(server.getUri());
            assertThat(restClient.get().retrieve().body(String.class), equalTo("Hello, World!"));
        }

        @Test
        @DisplayName("api - health endpoint returns health json")
        void testHealthEndpoint() {
            RestClient restClient = RestClient.create(server.getUri());
            assertThat(Objects.requireNonNull(restClient.get().uri("/health")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(Map.class))
                    .get("isAvailable"), equalTo(true));
        }
    }
}