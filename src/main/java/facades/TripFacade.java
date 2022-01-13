package facades;

import DTO.TripDTO;
import entities.Trip;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

public class TripFacade {
    private static EntityManagerFactory emf;
    private static TripFacade instance;
//creater trips og persister dem ned i DB
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();

        EntityManager em = emf.createEntityManager();
        try{

            Trip t1 = new Trip("Turen rundt", LocalDateTime.of(2022,2,20,18,00),"København",20,"sko, handsker");
            Trip t2 = new Trip("Turen af alle ture", LocalDateTime.of(2022,5,23,12,30),"København",60,"gode humør, sokker");

            em.getTransaction().begin();
            em.persist(t1);
            em.persist(t2);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
    }

    //Henter alle Trips ud
    public List<TripDTO> getAllTrips(){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery<Trip> tList = em.createQuery("SELECT t FROM Trip t", Trip.class);
            List<TripDTO> tripList = TripDTO.convertingTripList(tList.getResultList());
            em.getTransaction().commit();
            return tripList;
        } finally {
            em.close();
        }
    }
    //manager til at holde styr på hvad der bliver sendt til databasen og hvad der bliver sendt tilbage
    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    //Sørger for der altid kun er en instance af facaden
    public static TripFacade getTripFacade(EntityManagerFactory _emf){
        if (instance == null){
            emf = _emf;
            instance = new TripFacade();
        }
        return instance;
    }
}
