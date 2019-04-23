package com.studio.happyflower;

import com.studio.happyflower.model.entity.Plant;
import com.studio.happyflower.model.entity.User;
import com.studio.happyflower.model.repository.PlantRepository;
import com.studio.happyflower.model.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class HappyflowerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyflowerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, PlantRepository plantRepository) {
        return (args) -> {
            // save a couple of customers
            Set<Plant> plants = new HashSet<>();
            plants.add(new Plant("nazwa", true, LocalDateTime.now(), 8000, 0.5, 0.3, 0.7, 25));
            plants.add(new Plant("druga_roslinka", true, LocalDateTime.now(), 8000, 0.5, 0.3, 0.7, 25));
            userRepository.save(new User("Michal", "mwypych@mail.com", "dupa", plants));
            userRepository.save(new User("Chloe", "obrien@seventy.com", "haslo"));




//            repository.save(new Customer("Kim", "Bauer"));
//            repository.save(new Customer("David", "Palmer"));
//            repository.save(new Customer("Michelle", "Dessler"));

//            // fetch all customers
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (Customer customer : repository.findAll()) {
//                log.info(customer.toString());
//            }
//            log.info("");
//
//            // fetch an individual customer by ID
//            repository.findById(1L)
//                    .ifPresent(customer -> {
//                        log.info("Customer found with findById(1L):");
//                        log.info("--------------------------------");
//                        log.info(customer.toString());
//                        log.info("");
//                    });
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastName('Bauer'):");
//            log.info("--------------------------------------------");
//            repository.findByLastName("Bauer").forEach(bauer -> {
//                log.info(bauer.toString());
//            });
//            // for (Customer bauer : repository.findByLastName("Bauer")) {
//            // 	log.info(bauer.toString());
//            // }
//            log.info("");
        };
    }

}
