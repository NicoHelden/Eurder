package be.switchfully.webapi;

import be.switchfully.domain.item.CreateItemDTO;
import be.switchfully.domain.item.ItemDTO;
import be.switchfully.domain.user.Feature;
import be.switchfully.service.ItemService;
import be.switchfully.service.SecurityService;
import be.switchfully.service.exception.UnauthorizatedException;
import be.switchfully.service.exception.UnknownUserException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.*;

@SecuritySchemes(value = {
        @SecurityScheme(securitySchemeName = "SecurityScheme",
                type = SecuritySchemeType.HTTP,
                scheme = "Basic")}
)
@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

    private final ItemService itemService;
    SecurityService securityService;

    @Inject
    public ItemController(ItemService itemService, SecurityService securityService) {
        this.itemService = itemService;
        this.securityService = securityService;
    }

    @GET
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(
            operationId = "getAllItems",
            summary = "Get all items",
            description = "Retrieve a list of all items from the database"
    )
    public Response getAllItems(@HeaderParam("Authorization") String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_ALL_ITEMS);
        List<ItemDTO> items = itemService.getAllItems();
        return Response.status(OK).entity(items).build();
    }

    @POST
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(
            operationId = "createItem",
            summary = "Create a new Item",
            description = "Create a new Item to add inside the Database"
    )
    public Response createItem(@HeaderParam("Authorization") String authorization, CreateItemDTO createItemDTO) {
        securityService.validateAuthorization(authorization, Feature.ADD_ITEM);
        return Response.status(CREATED).entity(itemService.addItem(createItemDTO)).build();
    }
    @PUT
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(
            operationId = "updateItem",
            summary = "Update an existing Item",
            description = "Update an existing Item in the database"
    )
    public Response updateItem(@HeaderParam("Authorization") String authorization, ItemDTO itemToUpdate) {
        securityService.validateAuthorization(authorization, Feature.UPDATE_ITEM);
        return Response.status(OK).entity(itemService.updateItem(itemToUpdate)).build();
    }





    @ServerExceptionMapper(NotFoundException.class)
    protected Response notFoundException(NotFoundException exception) {
        return Response.status(BAD_REQUEST).entity(exception.getMessage()).build();
    }

    @ServerExceptionMapper(IllegalArgumentException.class)
    protected Response illegalArgumentException(IllegalArgumentException exception) {
        return Response.status(BAD_REQUEST).entity(exception.getMessage()).build();
    }

    @ServerExceptionMapper(UnknownUserException.class)
    protected Response unknownUserException(UnknownUserException exception) {
        return Response.status(BAD_REQUEST).entity("No user found with this id").build();
    }

    @ServerExceptionMapper(UnauthorizatedException.class)
    protected Response unauthorizedException(UnauthorizatedException exception) {
        return Response.status(FORBIDDEN).entity(exception.getMessage()).build();
    }

}
