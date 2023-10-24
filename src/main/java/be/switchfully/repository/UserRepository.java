package be.switchfully.repository;

import be.switchfully.domain.user.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.Optional;

import static be.switchfully.database.EurderDb.usersById;

@ApplicationScoped
public class UserRepository {

    public User save(User user) {
        usersById.put(user.getId(), user);
        return user;
    }

    public Collection<User> getAllCustomers() {return usersById.values();}

    public Optional<User> getUserById(String customerId) {return Optional.ofNullable(usersById.get(customerId));}
}
