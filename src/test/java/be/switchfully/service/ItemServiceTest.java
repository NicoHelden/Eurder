package be.switchfully.service;

import be.switchfully.domain.item.CreateItemDTO;
import be.switchfully.domain.item.Item;
import be.switchfully.domain.item.ItemDTO;
import be.switchfully.mapper.ItemMapper;
import be.switchfully.repository.ItemRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

class ItemServiceTest {

    ItemRepository itemRepository = mock(ItemRepository.class);
    ItemService itemService = new ItemService(itemRepository);

    @Test
    void givenItemDTO_whenAddItem_thenItemShouldBeAdded() {
        CreateItemDTO newItem = new CreateItemDTO();
        newItem.setName("Laptop");
        newItem.setDescription("High-end laptop");
        newItem.setPrice(1000.0);
        newItem.setAmount(10);

        ItemDTO savedItemDTO = new ItemDTO().setName("Laptop").setDescription("High-end laptop").setPrice(1000.0).setAmount(10);

        // Directly creating Item entity
        Item savedItemEntity = new Item("Laptop", "High-end laptop", 1000.0, 10);

        when(itemRepository.save(any(Item.class))).thenReturn(savedItemEntity);

        ItemDTO result = itemService.addItem(newItem);

        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(savedItemDTO);
    }
}


