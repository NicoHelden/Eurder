package be.switchfully.service;

import be.switchfully.domain.item.Item;
import be.switchfully.domain.item.ItemDTO;
import be.switchfully.repository.ItemRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemServiceTest {

    ItemRepository itemRepository = mock(ItemRepository.class);
    ItemService itemService = new ItemService(itemRepository);

    @Test
    void givenItemDTO_whenAddItem_thenItemShouldBeAdded() {
        ItemDTO newItem = new ItemDTO("Laptop", "High-end laptop", "High-end laptop", 1000.0, 10);
        ItemDTO savedItem = new ItemDTO("1", "Laptop", "High-end laptop", 1000.0, 10);

        when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

        ItemDTO result = itemService.addItem(newItem);

        assertThat(result).isEqualTo(savedItem);
    }
}