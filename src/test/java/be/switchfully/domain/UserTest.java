package be.switchfully.domain;

import static org.assertj.core.api.Assertions.assertThat;

import be.switchfully.domain.user.User;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void givenValidCustomerData_whenCreatingCustomer_thenCustomerShouldBeCreated() {
        // Given
        String firstName = "Nicolas";
        String lastName = "Heldenbergh";
        String email = "nciolas.heldenbergh@email.com";
        String address = "Some Street 1";
        String phoneNumber = "1234567890";
        String password = "123456";

        // When
        User user = new User(firstName, lastName, email, address, phoneNumber, password);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo(firstName);

    }
}
