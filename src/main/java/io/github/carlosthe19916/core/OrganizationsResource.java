package io.github.carlosthe19916.core;

import io.github.carlosthe19916.core.representations.idm.ComponentRepresentation;
import io.github.carlosthe19916.core.representations.idm.ExtendedOrganizationRepresentation;
import io.github.carlosthe19916.core.representations.idm.OrganizationRepresentation;
import io.github.carlosthe19916.core.representations.idm.OrganizationSearchQueryRepresentation;
import org.keycloak.representations.idm.KeysMetadataRepresentation;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface OrganizationsResource {

    @POST
    @Path("/")
    OrganizationRepresentation createOrganization(@Valid OrganizationRepresentation organizationRepresentation);

    @GET
    @Path("/")
    List<OrganizationRepresentation> getOrganizations(
            @QueryParam("organizationId") String organizationId,
            @QueryParam("filterText") String filterText,
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit);

    @POST
    @Path("/search")
    List<ExtendedOrganizationRepresentation> searchOrganizations(@Valid OrganizationSearchQueryRepresentation organizationQuery);

    @GET
    @Path("/{organizationId}")
    OrganizationRepresentation getOrganization(
            @PathParam("organizationId") String organizationId
    );

    @PUT
    @Path("/{organizationId}")
    OrganizationRepresentation updateOrganization(
            @PathParam("organizationId") String organizationId,
            OrganizationRepresentation rep
    );

    @DELETE
    @Path("/{organizationId}")
    void deleteOrganization(
            @PathParam("organizationId") String organizationId
    );

    @GET
    @Path("/{organizationId}/keys")
    @Produces(MediaType.APPLICATION_JSON)
    KeysMetadataRepresentation getKeyMetadata(
            @PathParam("organizationId") final String organizationId
    );

    @GET
    @Path("/{organizationId}/components")
    @Produces(MediaType.APPLICATION_JSON)
    List<ComponentRepresentation> getComponents(
            @PathParam("organizationId") final String organizationId,
            @QueryParam("parent") String parent,
            @QueryParam("type") String type
    );

    @POST
    @Path("/{organizationId}/components")
    @Consumes(MediaType.APPLICATION_JSON)
    Response createComponent(
            @PathParam("organizationId") final String organizationId, ComponentRepresentation rep
    );

    @GET
    @Path("/{organizationId}/components/{componentId}")
    @Produces(MediaType.APPLICATION_JSON)
    ComponentRepresentation getComponent(
            @PathParam("organizationId") final String organizationId,
            @PathParam("componentId") String componentId
    );

    @PUT
    @Path("/{organizationId}/components/{componentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateComponent(
            @PathParam("organizationId") final String organizationId,
            @PathParam("componentId") String componentId, ComponentRepresentation rep
    );

    @DELETE
    @Path("/{organizationId}/components/{componentId}")
    void removeComponent(
            @PathParam("organizationId") final String organizationId,
            @PathParam("componentId") String componentId
    );

}
