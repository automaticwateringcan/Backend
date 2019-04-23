package com.studio.happyflower.model.repository;

import com.studio.happyflower.model.entity.Plant;
import org.springframework.data.repository.CrudRepository;

public interface PlantRepository extends CrudRepository<Plant, Long> {
}
