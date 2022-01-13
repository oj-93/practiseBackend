package rest;

import entities.Guide;
import entities.Role;
import entities.Trip;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class GuideResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private Guide g1, g2, g3;

    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
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

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Guide").executeUpdate();
        em.createQuery("DELETE FROM Trip").executeUpdate();
        em.createQuery("DELETE FROM Role").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        User user = new User("user", "test1");
        user.addRole(userRole);
        User admin = new User("admin", "test2");
        admin.addRole(adminRole);
        User both = new User("user_admin", "test3");
        both.addRole(userRole);
        both.addRole(adminRole);
        g1 = new Guide("Test1","Male","1973","asd","asd");
        g2 = new Guide("Test2","Male","2000","adsd","afsd");
        g3 = new Guide("Test3","Male","2020","afsd","asgd");
        em.persist(g1);
        em.persist(g2);
        em.persist(g3);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.getTransaction().commit();
    }

    private static String securityToken;

    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    @Test
    void createGuide() {
        login("admin", "test2");
        String createdGuide = "{\n" +
                "   \"name\": \"Søren\", \n" +
                "   \"gender\": \"male\", \n" +
                "   \"birthYear\": \"1999\", \n" +
                "   \"profile\": \"siuuuu\", \n" +
                "   \"imageURL\": \"omajdfafuasf\" \n" +
                "}";
        given()

                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(createdGuide)
                .post("/guides/createGuide").then()
                .assertThat()
                .statusCode(200)
                .body("gender", is("male"));
    }
}


