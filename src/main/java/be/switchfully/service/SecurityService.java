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

import static java.lang.String.format;

@ApplicationScoped
public class SecurityService {
    @Inject
    UserRepository userRepository;
    private final Logger logger = Logger.getLogger(SecurityService.class);

    public void validateAuthorization(@Nullable String authorization, Feature feature) {
        DecodedCredentials credentials = getIdPassword(Optional.ofNullable(authorization)
                .orElseThrow(() -> new UnauthorizedException("Wrong credentials")));
        User user = userRepository.getUserById(credentials.id())
                .orElseThrow(() -> throwUserUnknownException(credentials.id()));

        if (!user.doesPasswordMatch(credentials.password())) {
            logger.errorf("Password does not match for user %s", credentials.id());
            throw new WrongPasswordException("Password does not match for user " + credentials.id());
        }

        if (!user.canHaveAccessTo(feature)) {
            logger.error(format("User %s does not have access to %s", credentials.id(), feature));
            throw new UnauthorizedException("User " + credentials.id() + " does not have access to " + feature);
        }

    }

    private UnknownUserException throwUserUnknownException(String id) {
        logger.errorf("Unknown user %s", id);
        return new UnknownUserException();
    }

    private DecodedCredentials getIdPassword(String authorization) {
        String decodedIdAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String id = decodedIdAndPassword.substring(0, decodedIdAndPassword.indexOf(":"));
        String password = decodedIdAndPassword.substring(decodedIdAndPassword.indexOf(":") + 1);
        return new DecodedCredentials(id, password);
    }

    public String getUserIdFromAuthorization(String authorization) {
        DecodedCredentials credentials = getIdPassword(authorization);
        String id = credentials.id();
        User user = userRepository.getUserById(id)
                .orElseThrow(() -> throwUserUnknownException(id));
        return user.getId();
    }

}
