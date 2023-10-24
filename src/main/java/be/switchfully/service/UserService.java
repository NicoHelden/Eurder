package be.switchfully.service;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.UserDTO;
import be.switchfully.mapper.UserMapper;
import be.switchfully.repository.UserRepository;
import be.switchfully.service.exception.InvalidEmailException;
import be.switchfully.service.exception.NonUniqueEmailException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static be.switchfully.database.EurderDb.usersById;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    public boolean existByEmail(String emailToCheck) {
        if (emailToCheck == null) {
            throw new IllegalArgumentException("Email must be filled");
        }
        return userRepository.getAllCustomers().stream()
                .anyMatch(customer -> customer.getEmail().equals(emailToCheck));
    }

    public boolean isActiveEmail(String emailToCheck) {
        try {
            URL url = new URL("https://api.emailable.com/v1/verify?email=" + emailToCheck + "&api_key=test_a4ecacdcc9c880c33e7a");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            if (status == 200) {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed email validation, " + e);
        }
        return false;
    }

    public boolean isValidEmail(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";
        //^(.+)@(\S+) $
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
    public UserDTO registerUser(CreateUserDTO createUserDTO) throws NonUniqueEmailException {
        if (existByEmail(createUserDTO.getEmail())) {
            throw new NonUniqueEmailException("This email already exists.");
        }
        if (!isValidEmail(createUserDTO.getEmail())) {
            throw new InvalidEmailException("This email is no valid.");
        }
        return UserMapper.mapToDTO(userRepository.save(UserMapper.mapToEntity(createUserDTO)));
    }
    public Collection<UserDTO> getAllUsers() {
        return userRepository.getAllCustomers().stream().map(UserMapper::mapToDTO).collect(Collectors.toSet());
    }
}
