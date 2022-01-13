package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String gender;
    private String birthYear;
    private String profile;
    private String imageURL;
    @OneToMany(mappedBy = "guide", cascade = CascadeType.PERSIST)
    private List<Trip> trips;

    public Guide() {
    }

    public Guide(String name, String gender, String birthYear, String profile, String imageURL) {
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.profile = profile;
        this.imageURL = imageURL;
        this.trips = new ArrayList<>();
    }

    public  void addTrip(Trip trip){
        if(trip != null){
            this.trips.add(trip);
            trip.setGuide(this);
        }
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
