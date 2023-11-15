package be.switchfully.service;

import be.switchfully.domain.item.CreateItemDTO;
import be.switchfully.domain.item.Item;
import be.switchfully.domain.item.ItemDTO;
import be.switchfully.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;
    @InjectMocks
    ItemService itemService;

    private CreateItemDTO newItem;
    private ItemDTO expectedItemDTO;

    @BeforeEach
    void setUp() {
        newItem = new CreateItemDTO("Laptop", "High-end laptop", 1000.0, 10);

        UUID expectedId = UUID.randomUUID(); // Generate a random UUID for testing
        expectedItemDTO = new ItemDTO(expectedId, "Laptop", "High-end laptop", 1000.0, 10);
    }

    @Test
    void givenItemDTO_whenAddItem_thenItemShouldBeAdded() {
        Item savedItemEntity = new Item("Laptop", "High-end laptop", 1000.0, 10);

        when(itemRepository.save(any(Item.class))).thenReturn(savedItemEntity);

        ItemDTO result = itemService.addItem(newItem);

        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedItemDTO);
    }

    // Additional tests for other methods...
}


