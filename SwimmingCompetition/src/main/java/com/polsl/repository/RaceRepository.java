package com.polsl.repository;

import org.springframework.data.repository.CrudRepository;
import com.polsl.entity.Race;

public interface RaceRepository extends CrudRepository<Race, Long> {
}
