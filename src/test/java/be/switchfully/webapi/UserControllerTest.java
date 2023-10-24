package be.switchfully.webapi;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.UserDTO;
import be.switchfully.service.SecurityService;
import be.switchfully.service.UserService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserService userService;
    private SecurityService securityService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        securityService = Mockito.mock(SecurityService.class);
        userController = new UserController(userService, securityService);
    }

    @Test
    void givenCreateUserDTO_whenRegisterUser_thenReturnResponse() {
        CreateUserDTO createUserDTO = new CreateUserDTO("John", "Doe", "john@example.com", "123 St", "555-1234", "password");
        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setId("1")
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john@example.com")
                .setAddress("123 St")
                .setPhoneNumber("555-1234");

        when(userService.registerUser(any(CreateUserDTO.class))).thenReturn(expectedUserDTO);

        Response response = userController.registerUser(createUserDTO);

        assertThat(response.getStatus()).isEqualTo(201); // HTTP Status CREATED
        assertThat(response.getEntity()).isEqualTo(expectedUserDTO);
    }

    @Test
    void givenAuthorizationHeader_whenGetAllUsers_thenReturnUserList() {
        UserDTO user1 = new UserDTO().setId("1").setFirstName("John");
        UserDTO user2 = new UserDTO().setId("2").setFirstName("Jane");
        List<UserDTO> expectedUsers = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(expectedUsers);

        Response response = userController.getAllUsers("someAuthorizationHeader");

        assertThat(response.getStatus()).isEqualTo(200); // HTTP Status OK
        assertThat(response.getEntity()).isEqualTo(expectedUsers);
    }
}