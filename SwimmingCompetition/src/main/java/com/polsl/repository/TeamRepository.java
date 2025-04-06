package com.polsl.repository;

import org.springframework.data.repository.CrudRepository;
import com.polsl.entity.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {
}
