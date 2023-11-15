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
        Item itemEntity = ItemMapper.mapToEntity(newItem);
        Item savedItem = itemRepository.save(itemEntity);
        return ItemMapper.mapToDTO(savedItem);
    }

    public ItemDTO updateItem(ItemDTO updatedItem) {
        Item itemEntity = ItemMapper.mapToEntity(updatedItem);
        Item savedItem = itemRepository.update(itemEntity);
        return ItemMapper.mapToDTO(savedItem);
    }
}
