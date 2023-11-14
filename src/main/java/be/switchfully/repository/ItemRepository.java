package be.switchfully.repository;

import be.switchfully.domain.item.Item;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ItemRepository implements PanacheRepositoryBase<Item, UUID> {

    @Transactional
    public Item save(Item item) {
        persist(item);
        return item;
    }

    @Transactional
    public Item update(Item item) {
        Optional<Item> existingItem = findByIdOptional(item.getId());
        if (existingItem.isPresent()) {
            return getEntityManager().merge(item);
        } else {
            throw new NotFoundException("Item with ID " + item.getId() + " not found.");
        }
    }

    public List<Item> getAll() {
        return listAll();
    }
}
