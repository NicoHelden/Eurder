package be.switchfully.repository;

import be.switchfully.domain.user.User;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    @Transactional
    void givenCustomer_whenSave_thenShouldReturnSavedCustomer() {
        // Given
        User user = new User("Nicolas", "Heldenbergh", "nicolas.heldenbergh@email.com", "Some Street 1", "1234567890", "123456");

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(savedUser.getLastName()).isEqualTo(user.getLastName());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getAddress()).isEqualTo(user.getAddress());
        assertThat(savedUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(savedUser.getRole()).isEqualTo(user.getRole());
    }

    @Test
    @Transactional
    void whenGetAllCustomers_thenShouldReturnListOfAllCustomers() {
        // Generate a unique email for the test
        String uniqueEmail = "user" + System.currentTimeMillis() + "@test.com";

        // Given
        User testUser = new User("Nicolas", "Heldenbergh", uniqueEmail, "Some Street 1", "1234567890", "123456");
        userRepository.save(testUser);

        // When
        List<User> users = userRepository.getAllCustomers();

        // Then
        assertThat(users).isNotEmpty();
        assertThat(users).anyMatch(user -> user.getEmail().equals(uniqueEmail));
    }

    @Test
    @Transactional
    void givenUserId_whenGetUserById_thenShouldReturnUser() {
        // Given
        User testUser = new User("Nicolas", "Heldenbergh", "nicolas@email.com", "Some Street 1", "1234567890", "123456");
        userRepository.save(testUser);

        // When
        Optional<User> foundUser = userRepository.getUserById(testUser.getId());

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(testUser);
    }

    @Test
    @Transactional
    void givenUserEmail_whenFindByEmail_thenShouldReturnUser() {
        // Generate a unique email
        String uniqueEmail = "nicolas" + UUID.randomUUID().toString() + "@email.com";

        // Given
        User testUser = new User("Nicolas", "Heldenbergh", uniqueEmail, "Some Street 1", "1234567890", "123456");
        userRepository.save(testUser);

        // When
        Optional<User> foundUser = userRepository.findByEmail(testUser.getEmail());

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(testUser);
    }

    @Test
    @Transactional
    void givenUpdatedUser_whenUpdate_thenShouldReturnUpdatedUser() {
        // Generate a unique email
        String uniqueEmail = "nicolas" + UUID.randomUUID().toString() + "@email.com";

        // Given
        User testUser = new User("Nicolas", "Heldenbergh", uniqueEmail, "Some Street 1", "1234567890", "123456");
        testUser = userRepository.save(testUser);
        UUID userId = testUser.getId();

        // Retrieve the entity from the database
        User userToUpdate = userRepository.findByIdOptional(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // Update the entity
        userToUpdate.setFirstName("Updated Name");

        // When
        User updatedUser = userRepository.update(userToUpdate);

        // Then
        assertThat(updatedUser.getFirstName()).isEqualTo("Updated Name");
    }

    @Test
    @Transactional
    void givenUserId_whenDeleteById_thenShouldDeleteUser() {
        // Generate a unique email
        String uniqueEmail = "nicolas" + UUID.randomUUID().toString() + "@email.com";

        // Given
        User testUser = new User("Nicolas", "Heldenbergh", uniqueEmail, "Some Street 1", "1234567890", "123456");
        userRepository.save(testUser);
        UUID userId = testUser.getId();

        // When
        userRepository.deleteById(userId);
        Optional<User> deletedUser = userRepository.getUserById(userId);

        // Then
        assertThat(deletedUser).isEmpty();
    }
}