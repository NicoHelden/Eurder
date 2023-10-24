package be.switchfully.repository;

import be.switchfully.domain.item.Item;
import jakarta.enterprise.context.ApplicationScoped;

import static be.switchfully.database.EurderDb.itemsById;

@ApplicationScoped
public class ItemRepository {
    public void save(Item item) {
        itemsById.put(item.getId(), item);
    }

}
