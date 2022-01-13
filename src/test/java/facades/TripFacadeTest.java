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
        t1 = new Trip("Test tur1", LocalDateTime.of(2022,2,20,18,00),"Test location",20,"Test,test,test");
        t2 = new Trip("Test tur2", LocalDateTime.of(2022,2,20,18,00),"Test location2",45,"test1,test2");
        t3 = new Trip("Test tur3", LocalDateTime.of(2022,2,20,18,00),"Test location",35,"test3, test4");


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
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void getAllTrips(){
        List<TripDTO> allTrips = tripFacade.getAllTrips();
        assertEquals(3,allTrips.size());

    }

}
