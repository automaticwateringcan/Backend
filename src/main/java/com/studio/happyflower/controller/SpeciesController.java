package com.studio.happyflower.controller;

import com.studio.happyflower.model.entity.Species;
import com.studio.happyflower.model.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/species")
@CrossOrigin
public class SpeciesController {

    @Autowired
    private SpeciesRepository speciesRepository;

    @GetMapping
    public Iterable findAll() {
        return speciesRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Species> species = speciesRepository.findById(id);
        if(species.isPresent()) {
            return ResponseEntity.ok().body(species);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Species> createPlant(@Valid @RequestBody Species plant) throws URISyntaxException {
        Species result = speciesRepository.save(plant);
        return ResponseEntity.created(new URI("/api/species/" + result.getId()))
                .body(result);
    }
}
