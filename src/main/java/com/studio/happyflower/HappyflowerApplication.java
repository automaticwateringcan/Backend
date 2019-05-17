package com.studio.happyflower;

import com.studio.happyflower.model.entity.Plant;
import com.studio.happyflower.model.entity.Species;
import com.studio.happyflower.model.entity.User;
import com.studio.happyflower.model.repository.SpeciesRepository;
import com.studio.happyflower.model.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class HappyflowerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyflowerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, SpeciesRepository speciesRepository) {
        return (args) -> {

            //tworzenie gatunków
            Species s1 = new Species("Storczyk", "Storczyki budzą zachwyt fantazyjnym kształtem kwiatów i wspaniałymi kolorami.", 30, 8000);
            Species s2 = new Species("Róża", "Róża to bez wątpienia najpopularniejsza, a zarazem jedna z najstarszych roślin ozdobnych na świecie.", 15, 10000);
            Species s3 = new Species("Tulipan", "Polski serial telewizyjny z roku 1986, przedstawiający perypetie uwodziciela-oszusta o przydomku Tulipan", 20, 6000);
            speciesRepository.save(s1);
            speciesRepository.save(s2);
            speciesRepository.save(s3);

            //tworzenie roślinek
            Set<Plant> plants1 = new HashSet<>();
            plants1.add(new Plant("Storczyk", true, LocalDateTime.now(), 8000, 55.0, 35.0, 22.0, 22, s1));
            plants1.add(new Plant("Storczyk", true, LocalDateTime.now(), 6000, 60.0, 35.0, 23.0, 23, s1));
            Set<Plant> plants2 = new HashSet<>();
            plants2.add(new Plant("Róża", true, LocalDateTime.now(), 500, 65.0, 60.0, 30.0, 20, s2));

            //tworzenie userów
            userRepository.save(new User("Adam", "adam@gmail.com", "12#", plants1));
            userRepository.save(new User("Chloe", "chloe@gmail.com", "safepassword", plants2));

        };
    }

}
