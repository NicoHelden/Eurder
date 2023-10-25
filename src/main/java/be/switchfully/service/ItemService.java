package be.switchfully.service;

import be.switchfully.domain.item.CreateItemDTO;
import be.switchfully.domain.item.Item;
import be.switchfully.domain.item.ItemDTO;
import be.switchfully.mapper.ItemMapper;
import be.switchfully.repository.ItemRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDTO addItem(CreateItemDTO newItem) {
        // Step 1: Convert CreateItemDTO to Item
        Item itemEntity = ItemMapper.mapToEntity(newItem);

        // Step 2: Save Item
        Item savedItem = itemRepository.save(itemEntity);

        // Step 3: Convert Item to ItemDTO
        return ItemMapper.mapToDTO(savedItem);
    }
    public ItemDTO updateItem(ItemDTO updatedItem) {
        // Convert ItemDTO to Item entity
        Item itemEntity = ItemMapper.mapToEntity(updatedItem);

        // Update the Item
        Item savedItem = itemRepository.update(itemEntity);

        // Convert Item to ItemDTO
        return ItemMapper.mapToDTO(savedItem);
    }
}
