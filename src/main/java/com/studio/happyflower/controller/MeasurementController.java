package com.studio.happyflower.controller;

import com.studio.happyflower.model.entity.Measurement;
import com.studio.happyflower.model.entity.User;
import com.studio.happyflower.model.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/measurements")
@CrossOrigin
public class MeasurementController
{
    @Autowired
    private MeasurementRepository measurementRepository;

    @GetMapping
    public Iterable findAll() {
        return measurementRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Measurement> measurement = measurementRepository.findById(id);
        if(measurement.isPresent()) {
            return ResponseEntity.ok().body(measurement);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Measurement> createMeasurement(@Valid @RequestBody Measurement measurement) throws URISyntaxException {
        Measurement result = measurementRepository.save(measurement);
        return ResponseEntity.created(new URI("/api/measurements/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Measurement> updateMeasurement(@PathVariable Long id, @Valid @RequestBody Measurement updatedMeasurement) {
        Optional<Measurement> measurementOptional = measurementRepository.findById(id);
        if(measurementOptional.isPresent()){
            Measurement measurement = measurementOptional.get();
            measurement.setPlant(updatedMeasurement.getPlant());
            measurement.setDateTime(updatedMeasurement.getDateTime());
            measurement.setHumidity(updatedMeasurement.getHumidity());
            measurement.setSoilMosture(updatedMeasurement.getSoilMosture());
            measurement.setTemperature(updatedMeasurement.getTemperature());
            measurementRepository.save(measurement);
            return ResponseEntity.ok().body(measurement);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteMeasurement(@PathVariable Long id) {
        measurementRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
