package facades;
import DTO.GuideDTO;
import entities.Guide;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class GuideFacade {
    private static EntityManagerFactory emf;
    private static GuideFacade instance;
    //creater guides og persister dem ned i DB
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();

        EntityManager em = emf.createEntityManager();

        Guide g1 = new Guide("Morten","male","1999","oki","urlIMG");
        Guide g2 = new Guide("louise","female","2000","oki","urlIMG");

        try{
            em.getTransaction().begin();
            em.persist(g1);
            em.persist(g2);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
    }
    public List<GuideDTO> getAllGuides(){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery<Guide> gList = em.createQuery("SELECT g FROM Guide g", Guide.class);
            List<GuideDTO> guideList = GuideDTO.convertingGuideList(gList.getResultList());
            em.getTransaction().commit();
            return guideList;
        } finally {
            em.close();
        }
    }
    //create Guide
    public GuideDTO createGuide (GuideDTO guideDTO){
        EntityManager em = emf.createEntityManager();
        Guide guide = new Guide(guideDTO.getName(), guideDTO.getGender(),guideDTO.getBirthYear(),guideDTO.getProfile(),guideDTO.getImageURL());

        try{
            em.getTransaction().begin();
            em.persist(guide);
            em.getTransaction().commit();
            return new GuideDTO(guide);
        }finally {
            em.close();
        }
    }

    //manager til at holde styr på hvad der bliver sendt til databasen og hvad der bliver sendt tilbage
    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    //Sørger for der altid kun er en instance af facaden
    public static GuideFacade getGuideFacade(EntityManagerFactory _emf){
        if (instance == null){
            emf = _emf;
            instance = new GuideFacade();
        }
        return instance;
    }
}

