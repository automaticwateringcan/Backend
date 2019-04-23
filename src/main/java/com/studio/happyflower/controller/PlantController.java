package com.studio.happyflower.controller;

import com.studio.happyflower.model.entity.Plant;
import com.studio.happyflower.model.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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

}
