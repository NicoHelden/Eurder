package be.switchfully.domain.user;

import java.util.UUID;

public record DecodedCredentials(String email, String password) {
}
