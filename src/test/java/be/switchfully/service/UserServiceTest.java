package be.switchfully.service;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.Role;
import be.switchfully.domain.user.User;
import be.switchfully.domain.user.UserDTO;
import be.switchfully.mapper.UserMapper;
import be.switchfully.repository.UserRepository;
import be.switchfully.service.exception.InvalidEmailException;
import be.switchfully.service.exception.NonUniqueEmailException;
import be.switchfully.service.exception.UnknownUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    /*
    @Test
    void givenUserId_whenGetUserById_thenReturnUserDTO() {
        User user = new User(
                "John",                 // firstName
                "Doe",                  // lastName
                "john.doe@example.com", // email
                "123 Main St",          // address
                "123-456-7890",         // phoneNumber
                Role.ADMIN,             // role
                "password123");          // password
        when(userRepositoryMock.getUserById("1")).thenReturn(Optional.of(user));

        UserDTO retrievedUser = userService.getUserById("1");

        assertThat(retrievedUser).isEqualTo(UserMapper.mapToDTO(user));
    }
     */

    /*@Test
    void givenNonExistentUserId_whenGetUserById_thenThrowUnknownUserException() {
        when(userRepositoryMock.getUserById("99")).thenReturn(Optional.empty());

        assertThrows(UnknownUserException.class, () -> userService.getUserById("99"));
    }

    @Test
    void givenEmailDoesNotExist_whenExistByEmail_thenShouldReturnFalse() {
        // Given
        String email = "nonexistent@email.com";
        when(userRepositoryMock.getAllCustomers()).thenReturn(Collections.emptyList());

        // When
        boolean exists = userService.existByEmail(email);

        // Then
        assertThat(exists).isFalse();
    }
     */

    @Test
    void givenNullEmail_whenExistByEmail_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> userService.existByEmail(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email must be filled");
    }

    @Test
    void givenInvalidEmail_whenIsValidEmail_thenShouldReturnFalse() {
        // Given & When & Then
        assertThat(userService.isValidEmail("invalid")).isFalse();
    }

    @Test
    void givenValidEmail_whenIsValidEmail_thenShouldReturnTrue() {
        // Given & When & Then
        assertThat(userService.isValidEmail("test@example.com")).isTrue();
    }

    @Test
    void givenValidUser_whenRegisterUser_thenShouldReturnUserDTO() {
        // Given
        CreateUserDTO createUserDTO = new CreateUserDTO("John", "Doe", "john.doe@email.com", "address", "123-456-7890", "password");
        User userToReturn = new User("John", "Doe", "john.doe@email.com", "address", "123-456-7890", Role.ADMIN, "password");
        when(userRepositoryMock.getAllCustomers()).thenReturn(Collections.emptyList());
        when(userRepositoryMock.save(any(User.class))).thenReturn(userToReturn); // Mock the save method

        // When
        UserDTO registeredUser = userService.registerUser(createUserDTO);

        // Then
        assertThat(registeredUser).isNotNull();
    }

    @Test
    void whenGetAllUsers_thenReturnListOfUserDTO() {
        // Given
        when(userRepositoryMock.getAllCustomers()).thenReturn(
                Collections.singletonList(new User("John", "Doe", "john.doe@email.com", "address", "123-456-7890", Role.ADMIN, "password"))
        );

        // When
        Collection<UserDTO> allUsers = userService.getAllUsers();

        // Then
        assertThat(allUsers).isNotEmpty();
    }
/*
    @Test
    void givenInvalidUserId_whenGetUserById_thenShouldThrowUnknownUserException() {
        // Given
        when(userRepositoryMock.getUserById("invalid")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.getUserById("invalid"))
                .isInstanceOf(UnknownUserException.class)
                .hasMessage("User not found");
    }
 */

}