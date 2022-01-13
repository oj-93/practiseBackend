package facades;

import DTO.GuideDTO;
import DTO.TripDTO;
import entities.Guide;
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

public class GuideFacadeTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static GuideFacade guideFacade;
    private Guide g1,g2,g3;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        guideFacade = GuideFacade.getGuideFacade(emf);
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        g1 = new Guide("Peter","Male","1973","asd","asd");
        g2 = new Guide("Oliver","male","2000","asdd","oljh");
        g3 = new Guide("Sara","female","1980","aedas","asdfgsf");
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Guide").executeUpdate();
        em.createQuery("DELETE FROM Trip").executeUpdate();
        em.createQuery("DELETE FROM Role").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.persist(g1);
        em.persist(g2);
        em.persist(g3);
        em.getTransaction().commit();
    }
    @AfterEach
    public void resetTestDB() {
        //Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Guide").executeUpdate();
        em.createQuery("DELETE FROM Trip").executeUpdate();
        em.createQuery("DELETE FROM Role").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void getAllGuides() {
        List<GuideDTO> allGuides = guideFacade.getAllGuides();
        assertEquals(3, allGuides.size());
    }

    @Test
    public void createGuide(){
        Guide g4 = new Guide("Søren","male","2000","adasd","asddda");
        em.getTransaction().begin();
        em.persist(g4);
        em.getTransaction().commit();
        assertEquals(4, guideFacade.getAllGuides().size());
    }

}

