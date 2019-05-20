package com.studio.happyflower.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "measurement_id")
    private long id;

    @Column(name = "date")
    LocalDateTime dateTime;

    @Column(name = "soil_mosture")
    double soilMosture;

    @Column(name = "humidity")
    double humidity;

    @Column(name = "temperature")
    double temperature;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    @JsonBackReference
    Plant plant;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getSoilMosture() {
        return soilMosture;
    }

    public void setSoilMosture(double soilMosture) {
        this.soilMosture = soilMosture;
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

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Measurement(LocalDateTime dateTime, double soilMosture, double humidity, double temperature) {
        this.dateTime = dateTime;
        this.soilMosture = soilMosture;
        this.humidity = humidity;
        this.temperature = temperature;
    }


    public Measurement() {
    }
}
