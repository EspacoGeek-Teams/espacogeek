package com.espacogeek.geek.Media;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@DisplayName("Tests for Media")
public class MediaTest {
    private HttpGraphQlTester tester;

    // Setup
    @BeforeEach
    public void initClient() {
        var client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8080/api")
                .build();
            
        tester = HttpGraphQlTester.create(client);
    }

    // Test Cases
    @DisplayName("Requests Tests for Serie")
    public class serieRequestTest {

        @Test
        void querySerie_whenEntityExists_shouldReturnAMediaSerieCategory() {
            // Arrange
            
            // Act

            // Assert
        }
    }
}
