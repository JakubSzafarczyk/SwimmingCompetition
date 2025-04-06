package com.polsl.repository;

import org.springframework.data.repository.CrudRepository;
import com.polsl.entity.Coach;

public interface CoachRepository extends CrudRepository<Coach, Long> {
}