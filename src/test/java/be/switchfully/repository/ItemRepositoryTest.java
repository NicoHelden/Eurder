package be.switchfully.repository;

import be.switchfully.domain.item.Item;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class ItemRepositoryTest {

    @Inject
    ItemRepository itemRepository;

    private Item testItem;

    @BeforeEach
    void setUp() {
        testItem = new Item("Test Item", "Description", 10.0, 5);
        itemRepository.save(testItem);
    }

    @Test
    @Transactional
    void whenSaveItem_thenShouldReturnSavedItem() {
        // Given
        Item newItem = new Item("NewItem", "New Description", 20.0, 10);

        // When
        Item savedItem = itemRepository.save(newItem);

        // Then
        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("NewItem");
        // Additional assertions...
    }

    @Test
    void whenGetAllItems_thenShouldReturnListOfItems() {
        // When
        List<Item> items = itemRepository.getAll();

        // Then
        assertThat(items).isNotEmpty();
        assertThat(items).contains(testItem);
    }

    @Test
    @Transactional
    void givenItemId_whenUpdateItem_thenShouldReturnUpdatedItem() {
        // Given
        Item originalItem = itemRepository.save(new Item("Original Name", "Description", 10.0, 5));
        itemRepository.getEntityManager().flush();

        // When
        UUID originalItemId = originalItem.getId();
        Item itemToUpdate = itemRepository.findById(originalItemId);
                //.orElseThrow(() -> new IllegalStateException("Item not found"));

        // Use the updateName method to change the name
        itemToUpdate.updateName("Updated Name");
        itemRepository.update(itemToUpdate);

        // Manually flush and clear the entity manager
        itemRepository.getEntityManager().flush();
        itemRepository.getEntityManager().clear();

        // Fetch the updated item again
        Item updatedItem = itemRepository.findById(originalItemId);
                //.orElseThrow(() -> new IllegalStateException("Item not found after update"));

        // Then
        assertThat(updatedItem.getName()).isEqualTo("Updated Name");
    }

}