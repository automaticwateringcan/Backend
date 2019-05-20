package com.studio.happyflower.controller;

import com.studio.happyflower.model.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meausrement")
@CrossOrigin
public class MeasurementController
{
    @Autowired
    private MeasurementRepository measurementRepository;

    @GetMapping
    public Iterable findAll() {
        return measurementRepository.findAll();
    }
}
