package com.studio.happyflower.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.studio.happyflower.model.entity.Plant;
import com.studio.happyflower.model.helper.Sensor;
import com.studio.happyflower.model.repository.PlantRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/plants")
@CrossOrigin
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;

    @GetMapping
    public Iterable findAll() {
        return plantRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Plant> plant = plantRepository.findById(id);
        if(plant.isPresent()) {
            return ResponseEntity.ok().body(plant);
        }
        else {
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
        if(plantOptional.isPresent()){
            Plant plant = plantOptional.get();
            plant.setName(updatedPlant.getName());
            plant.setAuto(updatedPlant.isAuto());
            plant.setLastWatering(updatedPlant.getLastWatering());
            plant.setWateringInterval(updatedPlant.getWateringInterval());
            plant.setSoilMosture(updatedPlant.getSoilMosture());
            plant.setSoilMostureLimit(updatedPlant.getSoilMostureLimit());
            plant.setHumidity(updatedPlant.getHumidity());
            plant.setTemperature(updatedPlant.getTemperature());
//            plant.setUser(updatedPlant.getUser());
            plantRepository.save(plant);
            return ResponseEntity.ok().body(plant);
        }
        else {
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
        System.out.println("\n\n"+sensor.toString()+"\n\n");
        if(plantOptional.isPresent()){
            Plant plant = plantOptional.get();
            plant.setSoilMosture(sensor.getSoilMosture());
            plant.setHumidity(sensor.getHumidity());
            plant.setTemperature(sensor.getTemperature());
//            plant.setUser(updatedPlant.getUser());
            plantRepository.save(plant);
            return ResponseEntity.ok().body(plant);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // TODO
    // tu chciałem spróbować wysyłać zwykłego JSONa zamiast Entity, potem można stworzyć klasę do nawadniania
    @PostMapping("/water/{id}")
    public ResponseEntity<Plant> water(@PathVariable Long id, @RequestParam Integer portions) throws JSONException {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()){
            Plant plantWatered = plantOptional.get();

            //sending REST request to Arduino to water the plant
            final String uri = "localhost:8080/emb/water/" + id.toString();
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("portion", portions);

            HttpEntity<JSONObject> entity = new HttpEntity<>(jsonObject, headers);

            System.out.println(entity);

//            ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);

            //TODO
            // Dodać handlowanie przypadków, kiedy podlewanie nie dojdzie do skutku

            plantWatered.setLastWatering(LocalDateTime.now());
            plantRepository.save(plantWatered);
            return ResponseEntity.ok().body(plantWatered);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
