package com.studio.happyflower.model.helper;

public class Sensor {
    private long id;
    private double soilMosture;
    private double humidity;
    private double temperature;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Sensor(long id, double soilMosture, double humidity, double temperature) {
        this.id = id;
        this.soilMosture = soilMosture;
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public Sensor() {
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", soilMosture=" + soilMosture +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                '}';
    }
}
