package com.polsl.repository;

import org.springframework.data.repository.CrudRepository;
import com.polsl.entity.Competition;

public interface CompetitionRepository extends CrudRepository<Competition, Long> {
}
