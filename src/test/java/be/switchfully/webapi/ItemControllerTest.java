package be.switchfully.webapi;

import be.switchfully.domain.item.CreateItemDTO;
import be.switchfully.domain.item.ItemDTO;
import be.switchfully.service.ItemService;
import be.switchfully.service.SecurityService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemControllerTest {
    private ItemController itemController;
    private ItemService mockItemService;
    private SecurityService mockSecurityService;

    @BeforeEach
    void setUp() {
        mockItemService = mock(ItemService.class);
        mockSecurityService = mock(SecurityService.class);
        itemController = new ItemController(mockItemService, mockSecurityService);
    }
    @Test
    void givenValidCreateItemDTO_whenCreateItem_thenItemShouldBeAdded() {
        // Given
        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setName("Laptop");
        createItemDTO.setDescription("High-end laptop");
        createItemDTO.setPrice(1000.0);
        createItemDTO.setAmount(10);

        ItemDTO expectedItemDTO = new ItemDTO().setName("Laptop").setDescription("High-end laptop").setPrice(1000.0).setAmount(10);
        when(mockItemService.addItem(any(CreateItemDTO.class))).thenReturn(expectedItemDTO);

        String authorization = "Basic SomeValidAuthToken";

        // When
        Response result = itemController.createItem(authorization, createItemDTO);

        // Then
        assertThat(result.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
        assertThat(result.getEntity()).isEqualTo(expectedItemDTO);
    }
    @Test
    void givenValidItemDTO_whenUpdateItem_thenItemShouldBeUpdated() {
        // Given
        ItemDTO existingItem = new ItemDTO().setName("Laptop").setDescription("High-end laptop").setPrice(1000.0).setAmount(10);
        ItemDTO updatedItem = new ItemDTO().setName("Updated Laptop").setDescription("Updated description").setPrice(1200.0).setAmount(5);

        when(mockItemService.updateItem(any(ItemDTO.class))).thenReturn(updatedItem);

        String authorization = "Basic SomeValidAuthToken";

        // When
        Response result = itemController.updateItem(authorization, existingItem);

        // Then
        assertThat(result.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(result.getEntity()).isEqualTo(updatedItem);
    }

}