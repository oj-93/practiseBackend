package facades;

import DTO.TripDTO;
import entities.Trip;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class TripFacadeTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static TripFacade tripFacade;
    private Trip t1,t2,t3;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        tripFacade = TripFacade.getTripFacade(emf);
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        em.createQuery("DELETE FROM Trip").executeUpdate();
        em.createQuery("DELETE FROM Role").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
         t1 = new Trip("Test","17/03","20:30","testLok1","20 min","sko, handsker");
         t2 = new Trip("test2", "10/01","12:00","testLok2","60 min","gode humør, sokker");
         t3 = new Trip("test3", "10/01","12:00","testLok3","50 min","gode humør, sokker");
         em.getTransaction().begin();
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);
        em.getTransaction().commit();
    }
    @AfterEach
    public void resetTestDB() {
        //Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Trip").executeUpdate();
        em.createQuery("DELETE FROM Role").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void getAllTrips(){
        List<TripDTO> allTrips = tripFacade.getAllTrips();
        assertEquals(3,allTrips.size());
    }

    @Test
    public void createTrip(){
         Trip t4 = new Trip("test4", "10/01","12:00","testLok4","50 min","gode humør, sokker");
         em.getTransaction().begin();
        em.persist(t4);
        em.getTransaction().commit();
        assertEquals(4, tripFacade.getAllTrips().size());
    }

}
