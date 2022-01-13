package rest;

import DTO.TripDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import facades.TripFacade;
import utils.EMF_Creator;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("trips")
public class TripResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final TripFacade FACADE = TripFacade.getTripFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @Path("all_trips")
    @GET
    @RolesAllowed("user")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllOwners() {
        List<TripDTO> list = FACADE.getAllTrips();
        return GSON.toJson(list);
    }

    @Path("createTrip")
    @POST
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createTrip(TripDTO tripDTO){
        TripDTO trip = FACADE.createTrip(tripDTO);
        return Response.ok(trip).build();
    }
}
