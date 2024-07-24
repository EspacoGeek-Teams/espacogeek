package com.espacogeek.geek.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.espacogeek.geek.config.WebTestClientConfig;

@DisplayName("Media Tests")
public abstract class RequestClient {
    protected HttpGraphQlTester tester;

    @BeforeEach
    public void initClient() {
        var client = WebTestClient.bindToServer()
                .baseUrl("http://127.0.0.1:8080/api")
                .defaultHeader("Authorization", "Basic dml0b3JAZ21haWwuY29tOnZpdG9yMTIzNA==")
                .build();
            
        tester = HttpGraphQlTester.create(client);
    }
}
