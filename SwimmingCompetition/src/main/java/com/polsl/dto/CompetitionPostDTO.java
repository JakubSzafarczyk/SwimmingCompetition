package com.polsl.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionPostDTO {
	@NotNull(message = "Name is required")
	private String name;
	
	private Timestamp startDate;
	
	private Timestamp endDate;
	
	private String description;
	
	private Long locationId;
	
	private Set<Long> raceIds;
	
	private Set<Long> competitorIds;
}
