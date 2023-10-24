package be.switchfully.database;

import be.switchfully.domain.item.Item;
import be.switchfully.domain.user.Role;
import be.switchfully.domain.user.User;

import java.util.concurrent.ConcurrentHashMap;

public class EurderDb {
    public static final ConcurrentHashMap<String, User> usersById = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Item> itemsById = new ConcurrentHashMap<>();

    private EurderDb() {
    }

    static {

        User user2 = new User("Donald", "Duck", "donald.duck@email.com", "Somewhere else", "1234567891", Role.ADMIN, "123");
        usersById.put(user2.getId(), user2);

        Item item = new Item("Harry Potter", "Fantasy book", 9.99, 5);
        itemsById.put(item.getId(), item);

    }
}


