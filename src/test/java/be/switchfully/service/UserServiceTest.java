package be.switchfully.service;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.User;
import be.switchfully.domain.user.UserDTO;
import be.switchfully.domain.user.Role;
import be.switchfully.repository.UserRepository;
import be.switchfully.service.exception.NonUniqueEmailException;
import be.switchfully.service.exception.UnknownUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private CreateUserDTO createUserDTO;
    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        createUserDTO = new CreateUserDTO("John", "Doe", "john@example.com", "123 Street", "1234567890", "password");
        userId = UUID.randomUUID();
        user = new User("John", "Doe", "john@example.com", "123 Street", "1234567890", Role.CUSTOMER, "password");
        user.setId(userId);
        UserDTO userDTO = new UserDTO(userId, "John", "Doe", "john@example.com", "123 Street", "1234567890", Role.CUSTOMER, "password");
    }

    @Test
    void registerUser_whenEmailAlreadyExists_thenThrowException() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.registerUser(createUserDTO))
                .isInstanceOf(NonUniqueEmailException.class)
                .hasMessageContaining("This email already exists.");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_whenValidUser_thenSaveUser() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.registerUser(createUserDTO);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("john@example.com");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers_whenCalled_thenReturnAllUsers() {
        when(userRepository.listAll()).thenReturn(Arrays.asList(user));

        var users = userService.getAllUsers();

        assertThat(users).isNotEmpty();
        assertThat(users).hasSize(1);

        verify(userRepository, times(1)).listAll();
    }

    @Test
    void getUserById_whenUserExists_thenReturnUser() {
        when(userRepository.findByIdOptional(userId)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);

        verify(userRepository, times(1)).findByIdOptional(userId);
    }

    @Test
    void getUserById_whenUserDoesNotExist_thenThrowException() {
        when(userRepository.findByIdOptional(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(UnknownUserException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, times(1)).findByIdOptional(userId);
    }
}
