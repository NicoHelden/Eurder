package be.switchfully.service;

import be.switchfully.domain.user.DecodedCredentials;
import be.switchfully.domain.user.Feature;
import be.switchfully.domain.user.User;
import be.switchfully.repository.UserRepository;
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

    public void validateAuthorization(@Nullable String authorization, Feature feature) {
        DecodedCredentials credentials = getIdPassword(Optional.ofNullable(authorization)
                .orElseThrow(() -> new UnauthorizedException("Wrong credentials")));


        UUID userId = UUID.fromString(credentials.id());


        User user = userRepository.findByIdOptional(userId)
                .orElseThrow(() -> throwUserUnknownException(userId));

        if (!user.doesPasswordMatch(credentials.password())) {
            logger.errorf("Password does not match for user %s", userId);
            throw new WrongPasswordException("Password does not match for user " + userId);
        }

        if (!user.canHaveAccessTo(feature)) {
            logger.errorf("User %s does not have access to %s", userId, feature);
            throw new UnauthorizedException("User " + userId + " does not have access to " + feature);
        }
    }

    private RuntimeException throwUserUnknownException(UUID userId) {
        logger.errorf("Unknown user %s", userId);
        return new UnknownUserException("User " + userId + " is unknown");
    }

    private DecodedCredentials getIdPassword(String authorization) {
        String decodedIdAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String id = decodedIdAndPassword.substring(0, decodedIdAndPassword.indexOf(":"));
        String password = decodedIdAndPassword.substring(decodedIdAndPassword.indexOf(":") + 1);
        return new DecodedCredentials(id, password);
    }


}
