package be.switchfully.service;

import be.switchfully.domain.item.ItemDTO;
import be.switchfully.repository.ItemRepository;

public class ItemService {
    public ItemService(ItemRepository itemRepository) {
    }

    public ItemDTO addItem(ItemDTO newItem) {
        return newItem;
    }
}
