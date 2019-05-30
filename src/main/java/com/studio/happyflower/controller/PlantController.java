package com.studio.happyflower.controller;

import com.studio.happyflower.model.entity.Measurement;
import com.studio.happyflower.model.entity.Plant;
import com.studio.happyflower.model.helper.Sensor;
import com.studio.happyflower.model.repository.MeasurementRepository;
import com.studio.happyflower.model.repository.PlantRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

@RestController
@RequestMapping("/api/plants")
@CrossOrigin
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @GetMapping
    public Iterable findAll() {
        return plantRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Plant> plant = plantRepository.findById(id);
        if (plant.isPresent()) {
            return ResponseEntity.ok().body(plant);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Plant> createPlant(@Valid @RequestBody Plant plant) throws URISyntaxException {
        Plant result = plantRepository.save(plant);
        return ResponseEntity.created(new URI("/api/plants/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Plant> updatePlant(@PathVariable Long id, @Valid @RequestBody Plant updatedPlant) {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if (plantOptional.isPresent()) {
            Plant plant = plantOptional.get();
            plant.setName(updatedPlant.getName());
            plant.setAuto(updatedPlant.isAuto());
            plant.setLastWatering(updatedPlant.getLastWatering());
            plant.setWateringInterval(updatedPlant.getWateringInterval());
            plant.setSoilMosture(updatedPlant.getSoilMosture());
            plant.setSoilMostureLimit(updatedPlant.getSoilMostureLimit());
            plant.setHumidity(updatedPlant.getHumidity());
            plant.setTemperature(updatedPlant.getTemperature());
            plant.setMeasurements(updatedPlant.getMeasurements());
//            plant.setUser(updatedPlant.getUser());
            plantRepository.save(plant);
            return ResponseEntity.ok().body(plant);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePlant(@PathVariable Long id) {
        plantRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // sensors
    @PutMapping("/updateSensor")
    ResponseEntity<Plant> updateSensor(@Valid @RequestBody Sensor sensor) {
        Optional<Plant> plantOptional = plantRepository.findById(sensor.getId());
        System.out.println("\n\n" + sensor.toString() + "\n\n");
        if (plantOptional.isPresent()) {
            // TODO jak zadziała to edytować entity Plant żeby zawierała pojedyńcze pole Meauserement i set Meaurement
            Plant plant = plantOptional.get();
            Measurement measurement = new Measurement(LocalDateTime.now(),sensor.getSoilMosture(), sensor.getHumidity(), sensor.getTemperature(), plant);
            plant.setSoilMosture(sensor.getSoilMosture());
            plant.setHumidity(sensor.getHumidity());
            plant.setTemperature(sensor.getTemperature());
            measurementRepository.save(measurement);
            plant.getMeasurements().add(measurement);
            plantRepository.save(plant);
            return ResponseEntity.ok().body(plant);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/water/{id}")
    public ResponseEntity<Plant> water(@PathVariable Long id, @RequestParam Integer portions) {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if (plantOptional.isPresent()) {

            // save portions to DB
            Plant plantToWater = plantOptional.get();
            plantToWater.setPortions(portions);
            plantRepository.save(plantToWater);

            return ResponseEntity.ok().body(plantToWater);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/water/{id}")
    public ResponseEntity<String> waterArduino(@PathVariable Long id) throws JSONException {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if (plantOptional.isPresent()) {
            Plant plantWatered = plantOptional.get();

            int portions = plantWatered.getPortions();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("portion", portions);
//            jsonObject.put("soilMoistureLimit", soilMostureLimit);


            System.out.println(jsonObject.toString());

            if (portions > 0) {
                plantWatered.setLastWatering(LocalDateTime.now());
                plantWatered.setPortions(0);

                plantRepository.save(plantWatered);
            }

            //TODO
            // Dodać handlowanie przypadków, kiedy podlewanie nie dojdzie do skutku


            return ResponseEntity.ok().body(jsonObject.toString());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/measure/{id}")
    public ResponseEntity<List<Measurement>> getMeauserements(@PathVariable Long id, @RequestParam int amount){
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()) {
            Plant plant = plantOptional.get();
            List<Measurement> measurements;
            if(amount <= plant.getMeasurements().size()){
                measurements = plant.getMeasurements().subList(plant.getMeasurements().size() - amount, plant.getMeasurements().size());
            }
            //TODO
            // Wykminić sensowny system wyjątków i zwracanych wtedy responsów
            else{
                measurements = new ArrayList<>();
            }
            return ResponseEntity.ok().body(measurements);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/humidity/{id}")
    public ResponseEntity<List<Double>> getHumidity(@PathVariable Long id, @RequestParam int amount){
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()) {
            Plant plant = plantOptional.get();
            List<Double> measurements;
            if(amount <= plant.getMeasurements().size()){
                measurements = plant.getMeasurements().subList(plant.getMeasurements().size() - amount, plant.getMeasurements().size()).stream().map(item -> item.getHumidity()).collect(Collectors.toList());
            }
            //TODO
            // Wykminić sensowny system wyjątków i zwracanych wtedy responsów
            else{
                measurements = new ArrayList<>(Collections.nCopies(amount-plant.getMeasurements().size(), 0d));
                measurements.addAll(plant.getMeasurements().stream().map(item-> item.getHumidity()).collect(Collectors.toList()));
            }

            System.out.println(measurements);
            return ResponseEntity.ok().body(measurements);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/temperature/{id}")
    public ResponseEntity<List<Double>> getTemperature(@PathVariable Long id, @RequestParam int amount){
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()) {
            Plant plant = plantOptional.get();
            List<Double> measurements;
            if(amount <= plant.getMeasurements().size()){
                measurements = plant.getMeasurements().subList(plant.getMeasurements().size() - amount, plant.getMeasurements().size()).stream().map(item -> item.getTemperature()).collect(Collectors.toList());
            }
            //TODO
            // Wykminić sensowny system wyjątków i zwracanych wtedy responsów
            else{
                measurements = new ArrayList<>(Collections.nCopies(amount-plant.getMeasurements().size(), 0d));
                measurements.addAll(plant.getMeasurements().stream().map(item-> item.getTemperature()).collect(Collectors.toList()));
            }
            System.out.println(measurements);
            return ResponseEntity.ok().body(measurements);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/soil/{id}")
    public ResponseEntity<List<Double>> getSoil(@PathVariable Long id, @RequestParam int amount){
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()) {
            Plant plant = plantOptional.get();
            List<Double> measurements;
            if(amount <= plant.getMeasurements().size()){
                measurements = plant.getMeasurements().subList(plant.getMeasurements().size() - amount, plant.getMeasurements().size()).stream().map(item -> item.getSoilMosture()).collect(Collectors.toList());
            }
            //TODO
            // Wykminić sensowny system wyjątków i zwracanych wtedy responsów
            else{
                measurements = new ArrayList<>(Collections.nCopies(amount-plant.getMeasurements().size(), 0d));
                measurements.addAll(plant.getMeasurements().stream().map(item-> item.getSoilMosture()).collect(Collectors.toList()));
            }

            System.out.println(measurements);
            return ResponseEntity.ok().body(measurements);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
