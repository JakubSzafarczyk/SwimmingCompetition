package com.polsl.repository;

import org.springframework.data.repository.CrudRepository;
import com.polsl.entity.Location;

public interface LocationRepository extends CrudRepository<Location, Long> {
}