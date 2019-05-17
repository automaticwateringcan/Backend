package com.studio.happyflower.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class SimpleController {
    @GetMapping("/test")
    public String homePage() {
        return "Hello world";
    }
}
