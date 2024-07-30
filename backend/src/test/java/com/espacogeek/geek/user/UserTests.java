package com.espacogeek.geek.user;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.espacogeek.geek.utils.RequestClient;

@Nested
@SpringBootTest
@DisplayName("User Tests")
public class UserTests {
    private final static String USERNAME_TEST = "UserTester";
    private final static String PASSWORD_TEST = "123";
    private final static String EMAIL_TEST = "userTest@gmail.com";

    @Nested
    @DisplayName("Methods")
    public class UserMethodTest {
        // TODO CREATE USER

        // TODO FIND USER

        // TODO EDIT USER PASSWORD

        // TODO EDIT USER USERNAME

        // TODO EDIT USER EMAIL

        // TODO DELETE USER
    }
    
    @Nested
    @DisplayName("Requests")
    public class UserRequestTest extends RequestClient {
        
        // TODO CREATE USER
        @Test
        @Order(1)
        void createUser_ifValidAllCredentials_shouldCreateUserSuccessfully() {
            var response = tester.documentName("user")
                    .variable("username", USERNAME_TEST)
                    .variable("email", EMAIL_TEST)
                    .variable("password", PASSWORD_TEST)
                    .execute()
                    .path("create")
                    .entity(String.class);

            assertTrue(response.get() == new String("201 CREATED"));
        }

        @Test
        void createUser_ifValidUsernameAndPasswordAndNoValidEmail_shouldNotCreateUser() {
            tester.documentName("user")
                .variable("username", USERNAME_TEST)
                .variable("email", "test")
                .variable("password", PASSWORD_TEST)
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertTrue(errors.size() == 1);
                    errors.forEach((error) -> {
                        assertTrue(error.getMessage() == new String("400 BAD_REQUEST"));
                    });
                });
        }

        // TODO FIND USER

        // TODO EDIT USER PASSWORD

        // TODO EDIT USER USERNAME

        // TODO EDIT USER EMAIL

        // TODO DELETE USER (@Order() and @AfterEach)
    }
}
