package com.espacogeek.geek.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.espacogeek.geek.models.UserModel;
import com.espacogeek.geek.services.UserService;
import com.espacogeek.geek.utils.RequestClient;

@Nested
@SpringBootTest
@DisplayName("User Tests")
public class UserTests {
    private final static String USERNAME_TEST = "UserTester";
    private final static String NEW_USERNAME_TEST = "UserTester2";
    private final static String PASSWORD_TEST = "tester!123";
    private final static String NEW_PASSWORD_TEST = "tester!1234";
    private final static String EMAIL_TEST = "userTest@gmail.com";
    private final static String NEW_EMAIL_TEST = "userTest2@gmail.com";

    @Autowired
    private UserService userService;

    @Nested
    @DisplayName("Ordered Requests")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    /**
     * ! This test has to be executed in order.
     */
    public class UserRequestTest extends RequestClient {

        @Test
        @Order(1)
        void createUser_ifValidUsernameAndEmailAndInvalidPassword_shouldNotCreateUser() {
            tester.documentName("createUser")
                .variable("username", USERNAME_TEST)
                .variable("email", EMAIL_TEST)
                .variable("password", "asddas")
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertTrue(errors.size() == 1);
                    errors.forEach((error) -> {
                        assertEquals(new String("400 BAD_REQUEST"), error.getMessage());
                    });
                });
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

        @Test
        @Order(3)
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
        @Order(4)
        void findUserById_shouldReturnUser() {
            var user = userService.findByIdOrUsernameContainsOrEmail(null, USERNAME_TEST, null);

            tester.documentName("findUser")
                .variable("id", user.getFirst().getId())
                .execute()
                .path("findUser")
                .entityList(UserModel.class)
                .hasSize(1);
        }

        @Test
        @Order(5)
        void editUserPasswordByUser_whenPasswordIsInvalid_shouldNotEditUserPassword() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(EMAIL_TEST+":"+PASSWORD_TEST).getBytes()));

            tester.documentName("editPasswordUser")
                .variable("actualPassword", PASSWORD_TEST)
                .variable("newPassword", "tester")
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertTrue(errors.size() == 1);
                    errors.forEach((error) -> {
                        assertEquals(new String("400 BAD_REQUEST"), error.getMessage());
                    });
                });
        }

        @Test
        @Order(6)
        void editUserPasswordByUser_whenPasswordIsValid_shouldEditUserPassword() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(EMAIL_TEST+":"+PASSWORD_TEST).getBytes()));

            var response = tester.documentName("editPasswordUser")
                .variable("actualPassword", PASSWORD_TEST)
                .variable("newPassword", NEW_PASSWORD_TEST)
                .execute()
                .path("editPassword")
                .entity(String.class);

            assertEquals(new String("200 OK"), response.get());
        }

        @Test
        @Order(7)
        void editUserPasswordByUser_whenActualPasswordIsInvalid_shouldNotEditUserPassword() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(EMAIL_TEST+":"+NEW_PASSWORD_TEST).getBytes()));

            tester.documentName("editPasswordUser")
                .variable("actualPassword", "aaaaa")
                .variable("newPassword", NEW_PASSWORD_TEST)
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertTrue(errors.size() == 1);
                    errors.forEach((error) -> {
                        assertEquals(new String("401 UNAUTHORIZED"), error.getMessage());
                    });
                });
        }

        @Test
        @Order(8)
        void editUserUsernameByUser_whenRightPassword_shouldEditUserUsername() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(EMAIL_TEST+":"+NEW_PASSWORD_TEST).getBytes()));

            var response = tester.documentName("editUsernameUser")
                .variable("password", NEW_PASSWORD_TEST)
                .variable("newUsername", NEW_USERNAME_TEST)
                .execute()
                .path("editUsername")
                .entity(String.class);


            assertEquals(new String("200 OK"), response.get());
        }

        @Test
        @Order(9)
        void editUserUsernameByUser_whenWrongPassword_shouldNotEditUserUsername() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(EMAIL_TEST+":"+NEW_PASSWORD_TEST).getBytes()));

            tester.documentName("editUsernameUser")
                .variable("password", "abcd")
                .variable("newUsername", NEW_USERNAME_TEST)
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertTrue(errors.size() == 1);
                    errors.forEach((error) -> {
                        assertEquals(new String("401 UNAUTHORIZED"), error.getMessage());
                    });
                });
        }

        @Test
        @Order(10)
        void editUserEmailByUser_whenRightPassword_shouldEditUserEmail() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(EMAIL_TEST+":"+NEW_PASSWORD_TEST).getBytes()));

            var response = tester.documentName("editEmailUser")
                .variable("password", NEW_PASSWORD_TEST)
                .variable("newEmail", NEW_EMAIL_TEST)
                .execute()
                .path("editEmail")
                .entity(String.class);


            assertEquals(new String("200 OK"), response.get());
        }

        @Test
        @Order(11)
        void editUserEmailByUser_whenPasswordIsValidAndInvalidEmail_shouldNotEditUserEmail() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(NEW_EMAIL_TEST+":"+NEW_PASSWORD_TEST).getBytes()));

            tester.documentName("editEmailUser")
                .variable("password", NEW_PASSWORD_TEST)
                .variable("newEmail", "tester")
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertTrue(errors.size() == 1);
                    errors.forEach((error) -> {
                        assertEquals(new String("400 BAD_REQUEST"), error.getMessage());
                    });
                });
        }

        @Test
        @Order(12)
        void editUserEmailByUser_whenWrongPasswordAndValidEmail_shouldNotEditUserEmail() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(NEW_EMAIL_TEST+":"+NEW_PASSWORD_TEST).getBytes()));

            tester.documentName("editEmailUser")
                .variable("password", "NEW_PASSWORD_TEST")
                .variable("newEmail", NEW_EMAIL_TEST)
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertTrue(errors.size() == 1);
                    errors.forEach((error) -> {
                        assertEquals(new String("401 UNAUTHORIZED"), error.getMessage());
                    });
                });
        }

        @Test
        @Order(13)
        void queryUserByUsername_shouldReturnUser() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(NEW_EMAIL_TEST+":"+NEW_PASSWORD_TEST).getBytes()));

            tester.documentName("findUser")
                .variable("username", NEW_USERNAME_TEST)
                .execute()
                .path("findUser")
                .entityList(UserModel.class)
                .hasSizeGreaterThan(0);
        }

        @Test
        @Order(14)
        void deleteUser_ifValidUsernameAndPasswordAndNoValidEmail_shouldDeleteUser() {
            tester = initClientWithAuth(Base64.getEncoder().encodeToString(new String(NEW_EMAIL_TEST+":"+NEW_PASSWORD_TEST).getBytes()));

            var response = tester.documentName("deleteUser")
                .variable("password", NEW_PASSWORD_TEST)
                .execute()
                .path("deleteUser")
                .entity(String.class);

            assertEquals(new String("200 OK"), response.get());
        }
    }
}
