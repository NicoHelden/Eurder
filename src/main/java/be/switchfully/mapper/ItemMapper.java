package be.switchfully.mapper;

import be.switchfully.domain.item.CreateItemDTO;
import be.switchfully.domain.item.Item;
import be.switchfully.domain.item.ItemDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemMapper {
    public static ItemDTO mapToDTO(Item item) {
        return new ItemDTO()
                .setId(item.getId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setPrice(item.getPrice())
                .setAmount(item.getAmount());
    }

    public static Item mapToEntity(CreateItemDTO createItemDTO) {

        return new Item(createItemDTO.getName(), createItemDTO.getDescription(), createItemDTO.getPrice(), createItemDTO.getAmount());
    }

    public static Item mapToEntity(ItemDTO itemDTO) {
        Item item = new Item(itemDTO.getName(), itemDTO.getDescription(), itemDTO.getPrice(), itemDTO.getAmount());
        item.setId(itemDTO.getId());
        return item;
    }
}
