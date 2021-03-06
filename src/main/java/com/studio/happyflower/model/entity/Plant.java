package com.studio.happyflower.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "plant_id")
    private long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "auto")
    private boolean auto;

    @Column(name = "last_watering")
    private LocalDateTime lastWatering;

    @Column(name = "watering_interval")
    private int wateringInterval;

    @Column(name = "soil_mosture")
    private double soilMosture;

    @Column(name = "soil_mosture_limit")
    private double soilMostureLimit;

    @Column(name = "humidity")
    private double humidity;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "portions")
    private int portions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "species_id")
//    @JsonBackReference
    private Species species;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Measurement> measurements;

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

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public LocalDateTime getLastWatering() {
        return lastWatering;
    }

    public void setLastWatering(LocalDateTime lastWatering) {
        this.lastWatering = lastWatering;
    }

    public int getWateringInterval() {
        return wateringInterval;
    }

    public void setWateringInterval(int wateringInterval) {
        this.wateringInterval = wateringInterval;
    }

    public double getSoilMosture() {
        return soilMosture;
    }

    public void setSoilMosture(double soilMosture) {
        this.soilMosture = soilMosture;
    }

    public double getSoilMostureLimit() {
        return soilMostureLimit;
    }

    public void setSoilMostureLimit(double soilMostureLimit) {
        this.soilMostureLimit = soilMostureLimit;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public Plant(String name, boolean auto, LocalDateTime lastWatering, int wateringInterval, double soilMosture, double soilMostureLimit, double humidity, double temperature) {
        this.name = name;
        this.auto = auto;
        this.lastWatering = lastWatering;
        this.wateringInterval = wateringInterval;
        this.soilMosture = soilMosture;
        this.soilMostureLimit = soilMostureLimit;
        this.humidity = humidity;
        this.temperature = temperature;
        this.portions = 0;
    }

    public Plant(String name, boolean auto, LocalDateTime lastWatering, int wateringInterval, double soilMosture, double soilMostureLimit, double humidity, double temperature, Species species) {
        this.name = name;
        this.auto = auto;
        this.lastWatering = lastWatering;
        this.wateringInterval = wateringInterval;
        this.soilMosture = soilMosture;
        this.soilMostureLimit = soilMostureLimit;
        this.humidity = humidity;
        this.temperature = temperature;
        this.portions = 0;
        this.species = species;
    }

    public Plant(String name, boolean auto, LocalDateTime lastWatering, int wateringInterval, double soilMosture, double soilMostureLimit, double humidity, double temperature, User user, Species species) {
        this.name = name;
        this.auto = auto;
        this.lastWatering = lastWatering;
        this.wateringInterval = wateringInterval;
        this.soilMosture = soilMosture;
        this.soilMostureLimit = soilMostureLimit;
        this.humidity = humidity;
        this.temperature = temperature;
        this.portions = 0;
        this.user = user;
        this.species = species;
    }

    public Plant(String name, boolean auto, LocalDateTime lastWatering, int wateringInterval, double soilMosture, double soilMostureLimit, double humidity, double temperature, int portions, User user, Species species, List<Measurement> measurements) {
        this.name = name;
        this.auto = auto;
        this.lastWatering = lastWatering;
        this.wateringInterval = wateringInterval;
        this.soilMosture = soilMosture;
        this.soilMostureLimit = soilMostureLimit;
        this.humidity = humidity;
        this.temperature = temperature;
        this.portions = portions;
        this.user = user;
        this.species = species;
        this.measurements = measurements;
    }

    public Plant(String name, boolean auto, LocalDateTime lastWatering, int wateringInterval, double soilMosture, double soilMostureLimit, double humidity, double temperature, Species species, List<Measurement> measurements) {
        this.name = name;
        this.auto = auto;
        this.lastWatering = lastWatering;
        this.wateringInterval = wateringInterval;
        this.soilMosture = soilMosture;
        this.soilMostureLimit = soilMostureLimit;
        this.humidity = humidity;
        this.temperature = temperature;
        this.species = species;
        this.measurements = measurements;
        for(Measurement measurement : measurements){
            measurement.setPlant(this);
        }
    }


    public Plant() {
    }
}