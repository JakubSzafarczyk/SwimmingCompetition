package com.polsl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.polsl.entity.Competitor;

public interface CompetitorRepository extends CrudRepository<Competitor, Long>{
	
	List<Competitor> findByLastName(String lastName);

}
