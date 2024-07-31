package com.espacogeek.geek.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.espacogeek.geek.models.UserModel;
import com.espacogeek.geek.services.UserService;
import com.espacogeek.geek.utils.RequestClient;

@Nested
@SpringBootTest
@DisplayName("User Tests")
public class UserTests {
    private final static String USERNAME_TEST = "UserTester";
    private final static String NEW_USERNAME_TEST = "UserTester2";
    private final static String PASSWORD_TEST = "123";
    private final static String NEW_PASSWORD_TEST = "1234";
    private final static String EMAIL_TEST = "userTest@gmail.com";

    @Autowired
    private UserService userService;
    
    @Nested
    @DisplayName("Requests")
    public class UserRequestTest extends RequestClient {

        @Test
        @Order(1)
        void createUser_ifValidAllCredentials_withoutAuth_shouldCreateUserSuccessfully() {
            var response = tester.documentName("createUser")
                    .variable("username", USERNAME_TEST)
                    .variable("email", EMAIL_TEST)
                    .variable("password", PASSWORD_TEST)
                    .execute()
                    .path("createUser")
                    .entity(String.class);

            assertEquals(new String("201 CREATED"), response.get());
        }
        
        @Test
        void deleteUser_ifValidUsernameAndPasswordAndNoValidEmail_shouldDeleteUser() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(EMAIL_TEST+":"+PASSWORD_TEST).getBytes()));

            var response = tester.documentName("deleteUser")
                .variable("password", PASSWORD_TEST)
                .execute()
                .path("deleteUser")
                .entity(String.class);
            
            System.out.println("Response: " + response.get());
            
            assertEquals(new String("200 OK"), response.get());
        }

        @Test
        @Order(2)
        void createUser_ifValidUsernameAndPasswordAndNoValidEmail_shouldNotCreateUser() {
            tester.documentName("createUser")
                .variable("username", USERNAME_TEST)
                .variable("email", "test")
                .variable("password", PASSWORD_TEST)
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertTrue(errors.size() == 1);
                    errors.forEach((error) -> {
                        assertEquals(new String("400 BAD_REQUEST"), error.getMessage());
                    });
                });
        }

        // TODO FIND USER
        @Test
        @Order(3)
        void findUserById_shouldNotCreateUser() {
            var user = userService.findByIdOrUsernameContainsOrEmail(null, USERNAME_TEST, null);

            tester.documentName("findUser")
                .variable("id", user.getFirst().getId())
                .execute()
                .path("findUser")
                .entityList(UserModel.class)
                .hasSize(1);
        }

        // TODO EDIT USER PASSWORD

        // TODO EDIT USER USERNAME

        // TODO EDIT USER EMAIL
    }
}
