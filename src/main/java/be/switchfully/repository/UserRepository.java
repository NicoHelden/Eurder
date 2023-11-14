package be.switchfully.repository;

import be.switchfully.domain.user.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, UUID> {

    public User save(User user) {
        if (user.getId() == null) {
            persist(user); // This is fine for a new entity without an ID
        } else {
            user = getEntityManager().merge(user); // For an existing entity
        }
        return user;
    }

    public List<User> getAllCustomers() {
        return listAll();
    }

    public Optional<User> getUserById(UUID userId) {
        return findByIdOptional(userId);
    }

    public Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }


    public User update(User user) {
        if (isPersistent(user)) {
            return getEntityManager().merge(user);
        } else {
            persist(user);
            return user;
        }
    }
    public Optional<User> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }


    public boolean deleteById(UUID userId) {
        Optional<User> userOptional = findByIdOptional(userId);
        userOptional.ifPresent(this::delete);
        return false;
    }
}
