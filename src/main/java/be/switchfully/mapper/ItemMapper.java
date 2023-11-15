package be.switchfully.mapper;

import be.switchfully.domain.item.CreateItemDTO;
import be.switchfully.domain.item.Item;
import be.switchfully.domain.item.ItemDTO;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class ItemMapper {

    // Converts an Item entity to an ItemDTO
    public static ItemDTO mapToDTO(Item item) {
        if (item == null) {
            return null;
        }
        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAmount());
    }

    // Converts a CreateItemDTO to an Item entity for new item creation
    public static Item mapToEntity(CreateItemDTO createItemDTO) {
        if (createItemDTO == null) {
            return null;
        }
        return new Item(
                createItemDTO.getName(),
                createItemDTO.getDescription(),
                createItemDTO.getPrice(),
                createItemDTO.getAmount());
    }

    // Converts an ItemDTO to an Item entity, used for updating existing items
    public static Item mapToEntity(ItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }
        Item item = new Item(
                itemDTO.getName(),
                itemDTO.getDescription(),
                itemDTO.getPrice(),
                itemDTO.getAmount());
        item.setId(itemDTO.getId()); // Set the ID for an existing item
        // => reasonable compromise or do I need to handle the DTO-to-entity conversion in the service layer?
        return item;
    }
}
