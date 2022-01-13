package facades;

import DTO.TripDTO;
import entities.Trip;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TripFacade{
    private static EntityManagerFactory emf;
    private static TripFacade instance;
//creater trips og persister dem ned i DB
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();

        EntityManager em = emf.createEntityManager();
        try{

            Trip t1 = new Trip("Turen rundt","17/03","20:30","København","20 min","sko, handsker");
            Trip t2 = new Trip("Turen af alle ture", "10/01","12:00","København","2 timer","gode humør, sokker");

            em.getTransaction().begin();
            em.persist(t1);
            em.persist(t2);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
    }
    //create Trip
    public TripDTO createTrip (TripDTO tripDTO){
        EntityManager em = emf.createEntityManager();
        Trip trip = new Trip(tripDTO.getName(), tripDTO.getDate(),tripDTO.getTime(),tripDTO.getLocation(),tripDTO.getDuration(),tripDTO.getPackingList());

        try{
            em.getTransaction().begin();
            em.persist(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }finally {
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

    public TripDTO deleteTrip(long id){
        EntityManager em = emf.createEntityManager();
        Trip trip = em.find(Trip.class, id);
        try{
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM TRIP WHERE id =?").setParameter(1, trip.getId()).executeUpdate();
            em.remove(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }finally {
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
