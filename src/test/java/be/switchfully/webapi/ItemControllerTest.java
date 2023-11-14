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

import static org.assertj.core.api.Assertions.assertThat;
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
        createItemDTO = new CreateItemDTO();
        createItemDTO.setName("Laptop");
        createItemDTO.setDescription("High-end laptop");
        createItemDTO.setPrice(1000.0);
        createItemDTO.setAmount(10);

        itemDTO = new ItemDTO();
        itemDTO.setName("Laptop");
        itemDTO.setDescription("High-end laptop");
        itemDTO.setPrice(1000.0);
        itemDTO.setAmount(10);

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
        doThrow(UnauthorizatedException.class).when(mockSecurityService).validateAuthorization(anyString(), any());

        Response result = itemController.createItem(authorizationHeader, createItemDTO);

        assertThat(result.getStatus()).isEqualTo(Response.Status.FORBIDDEN.getStatusCode());
        // Additional assertions...
    }

    // Additional tests for other scenarios...
}