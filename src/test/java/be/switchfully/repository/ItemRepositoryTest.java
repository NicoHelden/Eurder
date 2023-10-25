package be.switchfully.repository;

import be.switchfully.domain.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository;
    @BeforeEach
    void setUp() {
        itemRepository = new ItemRepository();
    }

    @Test
    void givenItem_whenSave_thenItemIsSaved() {
        // Given
        Item item = new Item("Laptop", "An awesome laptop", 1200.00, 10);

        Item savedItem = itemRepository.save(item);

        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("Laptop");
    }

}