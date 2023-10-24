package be.switchfully.repository;

import be.switchfully.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {

    @Test
    void givenCustomer_whenSave_thenShouldReturnSavedCustomer() {
        // Given
        UserRepository repository = new UserRepository();
        User user = new User("Nicolas", "Heldenbergh", "nicolas.heldenbergh@email.com", "Some Street 1", "1234567890", "123");

        // When
        User savedUser = repository.save(user);

        // Then
        assertThat(savedUser).isNotNull();
    }
}