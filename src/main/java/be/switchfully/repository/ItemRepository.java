package be.switchfully.repository;

import be.switchfully.domain.item.Item;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.Set;

import static be.switchfully.database.EurderDb.itemsById;

@ApplicationScoped
public class ItemRepository {
    public void save(Item item) {
        itemsById.put(item.getId(), item);
    }

    public Set<Item> getAll() {
        return new HashSet<>(itemsById.values());
    }
}
