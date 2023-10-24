package be.switchfully.service;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.Role;
import be.switchfully.domain.user.User;
import be.switchfully.repository.UserRepository;
import be.switchfully.service.exception.InvalidEmailException;
import be.switchfully.service.exception.NonUniqueEmailException;
import io.quarkus.test.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    private UserRepository userRepositoryMock;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        userService = new UserService();
        userService.userRepository = userRepositoryMock;
    }

    @Test
    void givenEmailExists_whenExistByEmail_thenShouldReturnTrue() {
        // Given
        String email = "test@email.com";
        when(userRepositoryMock.getAllCustomers()).thenReturn(
                Collections.singletonList(new User("firstName", "lastName", "test@email.com", "address", "phoneNumber", Role.CUSTOMER, "password"))
        );
        // When
        boolean exists = userService.existByEmail(email);
        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void givenInvalidEmail_whenRegisterUser_thenShouldThrowInvalidEmailException() {
        // Given
        CreateUserDTO createUserDTO = new CreateUserDTO("firstName", "lastName", "test@email.com", "address", "phoneNumber", "password");
        createUserDTO.setEmail("invalidEmail");

        // When & Then
        assertThatThrownBy(() -> userService.registerUser(createUserDTO))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("This email is no valid.");
    }

    @Test
    void givenEmailAlreadyExists_whenRegisterUser_thenShouldThrowNonUniqueEmailException() {
        // Given
        CreateUserDTO createUserDTO = new CreateUserDTO("firstName", "lastName", "test@email.com", "address", "phoneNumber", "password");
        String email = "existing@email.com";
        createUserDTO.setEmail(email);
        when(userRepositoryMock.getAllCustomers()).thenReturn(
                Collections.singletonList(new User("firstName", "lastName", "test@email.com", "address", "phoneNumber", Role.CUSTOMER, "password").setEmail(email))
        );

        // When & Then
        assertThatThrownBy(() -> userService.registerUser(createUserDTO))
                .isInstanceOf(NonUniqueEmailException.class)
                .hasMessage("This email already exists.");
    }

}