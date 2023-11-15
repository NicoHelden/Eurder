package be.switchfully.mapper;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.User;
import be.switchfully.domain.user.UserDTO;

public class UserMapper {

    public static UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getPassword());
    }

    public static User mapToEntity(CreateUserDTO createUserDTO) {

        return new User(
                createUserDTO.getFirstName(),
                createUserDTO.getLastName(),
                createUserDTO.getEmail(),
                createUserDTO.getAddress(),
                createUserDTO.getPhoneNumber(),
                createUserDTO.getPassword()
        );
    }
}
