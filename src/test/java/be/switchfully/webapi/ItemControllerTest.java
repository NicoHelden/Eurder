package be.switchfully.webapi;

import be.switchfully.domain.item.CreateItemDTO;
import be.switchfully.domain.item.ItemDTO;
import be.switchfully.service.ItemService;
import be.switchfully.service.SecurityService;
import be.switchfully.service.exception.UnauthorizatedException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService mockItemService;
    @Mock
    private SecurityService mockSecurityService;
    @InjectMocks
    private ItemController itemController;

    private CreateItemDTO createItemDTO;
    private ItemDTO itemDTO;
    private String authorizationHeader;

    @BeforeEach
    void setUp() {
        createItemDTO = new CreateItemDTO("Laptop", "High-end laptop", 1000.0, 10);

        UUID itemId = UUID.randomUUID(); // Generate a random UUID for testing
        itemDTO = new ItemDTO(itemId, "Laptop", "High-end laptop", 1000.0, 10);

        authorizationHeader = "Basic SomeValidAuthToken";
    }

    @Test
    void givenValidCreateItemDTO_whenCreateItem_thenItemShouldBeAdded() {
        when(mockItemService.addItem(any(CreateItemDTO.class))).thenReturn(itemDTO);

        Response result = itemController.createItem(authorizationHeader, createItemDTO);

        assertThat(result.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
        assertThat(result.getEntity()).isEqualTo(itemDTO);
    }

    @Test
    void givenValidItemDTO_whenUpdateItem_thenItemShouldBeUpdated() {
        when(mockItemService.updateItem(any(ItemDTO.class))).thenReturn(itemDTO);

        Response result = itemController.updateItem(authorizationHeader, itemDTO);

        assertThat(result.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(result.getEntity()).isEqualTo(itemDTO);
    }

    @Test
    void givenUnauthorized_whenCreateItem_thenThrowUnauthorizatedException() {
        // Setup the conditions to throw UnauthorizatedException
        doThrow(UnauthorizatedException.class)
                .when(mockSecurityService)
                .validateAuthorization(anyString(), any());

        // Act - Call the method that should throw the exception
        Response result = null;
        try {
            result = itemController.createItem(authorizationHeader, createItemDTO);
            fail("Expected an UnauthorizatedException to be thrown");
        } catch (UnauthorizatedException e) {
            // Assert - Verify that the exception was thrown
            assertThat(e).isInstanceOf(UnauthorizatedException.class);
        }

    }
}