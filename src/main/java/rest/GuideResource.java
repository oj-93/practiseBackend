package rest;

import DTO.GuideDTO;
import DTO.TripDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.GuideFacade;
import facades.TripFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("guides")
public class GuideResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final GuideFacade FACADE = GuideFacade.getGuideFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("createGuide")
    @POST
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createGuide(GuideDTO guideDTO){
        System.out.println(guideDTO);
        GuideDTO guide = FACADE.createGuide(guideDTO);
        return Response.ok(guide).build();
    }
}
