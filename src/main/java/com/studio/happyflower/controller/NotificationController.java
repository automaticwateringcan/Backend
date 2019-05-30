package com.studio.happyflower.controller;

import com.studio.happyflower.model.entity.Plant;
import com.studio.happyflower.model.repository.PlantRepository;
import com.studio.happyflower.notification.AndroidPushNotificationsService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequestMapping("/api")
@RestController
public class NotificationController {
    private final String TOPIC = "Refill";

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @Autowired
    private PlantRepository plantRepository;

    @GetMapping("/plants/waterLevel/{id}")
    public ResponseEntity<String> waterLevel(@PathVariable Long id, @RequestParam boolean refillWater) throws JSONException {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if(plantOptional.isPresent()) {
            Plant plant = plantOptional.get();
            String plantName = plant.getName();

            JSONObject body = new JSONObject();
            body.put("to", "/topics/" + TOPIC);
            body.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("title", "JSA Notification");
            notification.put("body", "Proszę uzupełnić wodę w roślinie: " + plantName + "!");

            JSONObject data = new JSONObject();
            data.put("Key-1", "JSA Data 1");
            data.put("Key-2", "JSA Data 2");

            body.put("notification", notification);
            body.put("data", data);

            HttpEntity<String> request = new HttpEntity<>(body.toString());

            CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
            CompletableFuture.allOf(pushNotification).join();

            try {
                String firebaseResponse = pushNotification.get();

                return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
