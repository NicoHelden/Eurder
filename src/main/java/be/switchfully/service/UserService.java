package be.switchfully.service;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.User;
import be.switchfully.domain.user.UserDTO;
import be.switchfully.mapper.UserMapper;
import be.switchfully.repository.UserRepository;
import be.switchfully.service.exception.NonUniqueEmailException;
import be.switchfully.service.exception.UnknownUserException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    public boolean existByEmail(String emailToCheck) {
        if (emailToCheck == null || emailToCheck.isBlank()) {
            throw new IllegalArgumentException("Email must be filled");
        }
        return userRepository.findByEmail(emailToCheck).isPresent();
    }

    public boolean isValidEmail(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public UserDTO registerUser(CreateUserDTO createUserDTO) {
        if (existByEmail(createUserDTO.getEmail())) {
            throw new NonUniqueEmailException("This email already exists.");
        }
        if (!isValidEmail(createUserDTO.getEmail())) {
            throw new IllegalArgumentException("This email is not valid.");
        }
        User newUser = UserMapper.mapToEntity(createUserDTO);

        newUser = userRepository.save(newUser);
        return UserMapper.mapToDTO(newUser);
    }

    public Collection<UserDTO> getAllUsers() {
        return userRepository.listAll().stream()
                .map(UserMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID userId) {
        Optional<User> optionalUser = userRepository.findByIdOptional(userId);
        if (optionalUser.isEmpty()) {
            throw new UnknownUserException("User not found");
        }
        return UserMapper.mapToDTO(optionalUser.get());
    }
}
