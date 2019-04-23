package com.studio.happyflower.model.repository;

import com.studio.happyflower.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
