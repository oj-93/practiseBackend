package DTO;

import entities.Guide;
import entities.Trip;

import java.util.ArrayList;
import java.util.List;

public class GuideDTO {
    private long id;
    private String name;
    private String gender;
    private String birthYear;
    private String profile;
    private String imageURL;

    public GuideDTO(String name, String gender, String birthYear, String profile, String imageURL) {
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.profile = profile;
        this.imageURL = imageURL;
    }

    public GuideDTO(Guide guide){
        this.id = guide.getId();
        this.name = guide.getName();
        this.gender = guide.getGender();
        this.birthYear = guide.getBirthYear();
        this.profile = guide.getProfile();
        this.imageURL = guide.getImageURL();
    }

    public GuideDTO() {
    }

    public static List<GuideDTO> convertingGuideList(List<Guide> guide){
        List<GuideDTO> guideDTO = new ArrayList<>();
        if (guide != null){
            guide.forEach(guideEntity -> guideDTO.add(new GuideDTO(guideEntity)));
        }
        return guideDTO;
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
