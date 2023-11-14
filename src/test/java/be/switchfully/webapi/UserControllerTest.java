package be.switchfully.webapi;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.UserDTO;
import be.switchfully.service.SecurityService;
import be.switchfully.service.UserService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestHTTPEndpoint(UserController.class)
public class UserControllerTest {

    @InjectMock
    UserService userService;

    @InjectMock
    SecurityService securityService;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void testRegisterUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO("John", "Doe", "john@example.com", "123 Street", "1234567890", "password");
        UserDTO userDTO = new UserDTO(); // Initialize with user details

        Mockito.when(userService.registerUser(any())).thenReturn(userDTO);

        given()
                .contentType("application/json")
                .body(createUserDTO)
                .when().post()
                .then()
                .statusCode(201);
    }

    @Test
    public void testGetAllUsers() {
        Mockito.when(userService.getAllUsers()).thenReturn(Collections.singletonList(new UserDTO()));

        given()
                .when().get()
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(1));
    }

    @Test
    public void testGetUserById() {
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = new UserDTO(); // Initialize with user details
        Mockito.when(userService.getUserById(userId)).thenReturn(userDTO);

        given()
                .when().get("/" + userId.toString())
                .then()
                .statusCode(200);
    }
}