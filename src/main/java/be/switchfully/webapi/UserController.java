package be.switchfully.webapi;

import be.switchfully.domain.user.CreateUserDTO;
import be.switchfully.domain.user.Feature;
import be.switchfully.domain.user.UserDTO;
import be.switchfully.service.SecurityService;
import be.switchfully.service.UserService;
import be.switchfully.service.exception.NonUniqueEmailException;
import be.switchfully.service.exception.UnauthorizatedException;
import be.switchfully.service.exception.UnknownUserException;
import be.switchfully.service.exception.WrongPasswordException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.Collection;

import static jakarta.ws.rs.core.Response.Status.*;


@SecuritySchemes(value = {
        @SecurityScheme(securitySchemeName = "SecurityScheme",
                type = SecuritySchemeType.HTTP,
                scheme = "Basic")}
)
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    UserService userService;
    SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @POST
    @Operation(
            operationId = "register User",
            summary = "Register a new User",
            description = "Register a new User as Customer to add to the Database"
    )
    public jakarta.ws.rs.core.Response registerUser(CreateUserDTO createUserDTO) {
        try {
            return jakarta.ws.rs.core.Response.status(CREATED).entity(userService.registerUser(createUserDTO)).build();
        } catch (RuntimeException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @GET
    @Operation(
            operationId = "getAllUsers",
            summary = "Get All Users",
            description = "Get all Users of the Database"
    )
    @SecurityRequirement(name = "SecurityScheme")
    public Response getAllUsers(@HeaderParam("Authorization") String authorization) {
        securityService.validateAuthorization(authorization, Feature.GET_ALL_USERS);
        Collection<UserDTO> users = userService.getAllUsers();
        return Response.ok(users).build();
    }

    @ServerExceptionMapper(UnauthorizatedException.class)
    protected Response unauthorizedException(UnauthorizatedException exception) {
        return Response.status(FORBIDDEN).entity(exception.getMessage()).build();
    }

    @ServerExceptionMapper(UnknownUserException.class)
    protected Response unknownUserException(UnknownUserException exception) {
        return Response.status(FORBIDDEN).entity(exception.getMessage()).build();
    }

    @ServerExceptionMapper(WrongPasswordException.class)
    protected Response wrongPasswordException(WrongPasswordException exception) {
        return Response.status(FORBIDDEN).entity(exception.getMessage()).build();
    }

}
