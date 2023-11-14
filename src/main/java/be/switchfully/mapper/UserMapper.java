package be.switchfully.mapper;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.User;
import be.switchfully.domain.user.UserDTO;

public class UserMapper {

    public static UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        return userDTO;
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
