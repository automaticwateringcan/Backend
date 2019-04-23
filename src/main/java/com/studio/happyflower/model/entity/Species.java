package com.studio.happyflower.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Species {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "species_id")
    private long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "def_soil_mosture_limit")
    private double defSoilMostureLimit;

    @Column(nullable = false, name = "def_watering_interval")
    private int defWateringInterval;

//    @OneToMany(mappedBy = "species", cascade = CascadeType.PERSIST)
//    @JsonManagedReference
//    private Set<Plant> plants;

    public Species(String name, String description, double defSoilMostureLimit, int defWateringInterval) {
        this.name = name;
        this.description = description;
        this.defSoilMostureLimit = defSoilMostureLimit;
        this.defWateringInterval = defWateringInterval;
    }

//    public Species(String name, String description, double defSoilMostureLimit, int defWateringInterval, Set<Plant> plants) {
//        this.name = name;
//        this.description = description;
//        this.defSoilMostureLimit = defSoilMostureLimit;
//        this.defWateringInterval = defWateringInterval;
//        this.plants = plants;
//    }

    public Species() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDefSoilMostureLimit() {
        return defSoilMostureLimit;
    }

    public void setDefSoilMostureLimit(double defSoilMostureLimit) {
        this.defSoilMostureLimit = defSoilMostureLimit;
    }

    public int getDefWateringInterval() {
        return defWateringInterval;
    }

    public void setDefWateringInterval(int defWateringInterval) {
        this.defWateringInterval = defWateringInterval;
    }

//    public Set<Plant> getPlants() {
//        return plants;
//    }

//    public void setPlants(Set<Plant> plants) {
//        this.plants = plants;
//    }
}
