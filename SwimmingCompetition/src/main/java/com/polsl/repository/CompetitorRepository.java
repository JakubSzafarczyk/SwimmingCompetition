package com.polsl.repository;

import org.springframework.data.repository.CrudRepository;
import com.polsl.entity.Competitor;

public interface CompetitorRepository extends CrudRepository<Competitor, Long> {
}
