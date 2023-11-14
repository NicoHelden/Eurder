package be.switchfully.service;

import be.switchfully.domain.user.DecodedCredentials;
import be.switchfully.domain.user.Feature;
import be.switchfully.domain.user.User;
import be.switchfully.repository.UserRepository;
import be.switchfully.service.exception.UnauthorizatedException;
import be.switchfully.service.exception.UnauthorizedException;
import be.switchfully.service.exception.UnknownUserException;
import be.switchfully.service.exception.WrongPasswordException;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@ApplicationScoped
public class SecurityService {
    @Inject
    UserRepository userRepository;
    private final Logger logger = Logger.getLogger(SecurityService.class);

    public void validateAuthorization(String authorization, Feature feature) {
        if (authorization == null || !authorization.toLowerCase().startsWith("basic ")) {
            throw new UnauthorizedException("Invalid authorization header format.");
        }

        DecodedCredentials credentials = getIdPassword(authorization);

        // Retrieve the user by email
        User user = userRepository.findByEmail(credentials.email())
                .orElseThrow(() -> new UnknownUserException("User with email " + credentials.email() + " not found"));

        if (!user.doesPasswordMatch(credentials.password())) {
            logger.errorf("Password does not match for user with email %s", credentials.email());
            throw new WrongPasswordException("Password does not match for user with email " + credentials.email());
        }

        if (!user.canHaveAccessTo(feature)) {
            logger.errorf("User with email %s does not have access to feature %s", credentials.email(), feature);
            throw new UnauthorizedException("User with email " + credentials.email() + " does not have access to feature " + feature);
        }
    }

    private RuntimeException throwUserUnknownException(UUID userId) {
        logger.errorf("Unknown user %s", userId);
        return new UnknownUserException("User " + userId + " is unknown");
    }

    private DecodedCredentials getIdPassword(String authorization) {
        if (authorization == null || !authorization.startsWith("Basic ")) {
            throw new UnauthorizedException("Authorization header must be provided and must be Basic.");
        }

        String base64Credentials = authorization.substring("Basic ".length());
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded);

        String[] parts = credentials.split(":", 2);

        if (parts.length != 2) {
            throw new UnauthorizedException("Credentials are not in the proper format.");
        }

        String email = parts[0];
        String password = parts[1];

        return new DecodedCredentials(email, password);
    }


}
