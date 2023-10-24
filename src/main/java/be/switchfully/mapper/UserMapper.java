package be.switchfully.mapper;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.User;
import be.switchfully.domain.user.UserDTO;

public class UserMapper {
    public static UserDTO mapToDTO(User user) {
        return new UserDTO()
                .setId((user.getId()))
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setAddress(user.getAddress())
                .setPhoneNumber(user.getPhoneNumber())
                .setRole(user.getRole())
                .setPassword(user.getPassword());

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
