package be.switchfully.repository;

import be.switchfully.domain.item.Item;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static be.switchfully.database.EurderDb.itemsById;

@ApplicationScoped
public class ItemRepository {

    private final Map<String, Item> itemsById;

    public ItemRepository() {
        itemsById = new HashMap<>();
    }

    public Item save(Item item) {
        if (itemsById.containsKey(item.getId())) {
            throw new IllegalArgumentException("Item with the same ID already exists.");
        }
        itemsById.put(item.getId(), item);
        return item;
    }
    public Item update(Item item) {
        if (!itemsById.containsKey(item.getId())) {
            throw new NotFoundException("Item with the given ID not found.");
        }
        itemsById.put(item.getId(), item);
        return item;
    }

    public Set<Item> getAll() {
        return new HashSet<>(itemsById.values());
    }
}
