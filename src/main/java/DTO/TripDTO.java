package DTO;

import entities.Trip;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TripDTO {
    private long id;
    private String name;
    private String date;
    private String time;
    private String location;
    private String duration;
    private String packingList;

    public TripDTO(String name, String date, String time, String location, String duration, String packingList) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.duration = duration;
        this.packingList = packingList;
    }

    public TripDTO() {
    }

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.date = trip.getDate();
        this.time = trip.getTime();
        this.location = trip.getLocation();
        this.duration = trip.getDuration();
        this.packingList = trip.getPackingList();
    }

    public static List<TripDTO> convertingTripList(List<Trip> trip) {
        List<TripDTO> tripDTO = new ArrayList<>();
        if (trip != null) {
            trip.forEach(tripentity -> tripDTO.add(new TripDTO(tripentity)));
        }
        return tripDTO;
    }
}
