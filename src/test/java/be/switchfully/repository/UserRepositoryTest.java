package be.switchfully.repository;

import be.switchfully.domain.user.User;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

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
}