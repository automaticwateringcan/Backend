package com.studio.happyflower.model.repository;

import com.studio.happyflower.model.entity.Species;
import org.springframework.data.repository.CrudRepository;

public interface SpeciesRepository extends CrudRepository<Species, Long> {
}
