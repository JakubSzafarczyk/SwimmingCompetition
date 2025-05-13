package com.polsl.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionPutDTO {
	@NotNull(message = "Name is required")
	private String name;
	
	@NotNull(message = "Start Date is required")
	private Timestamp startDate;
	
	@NotNull(message = "End Date is required")
	private Timestamp endDate;
	
	@NotNull(message = "Description is required")
	private String description;
	
	@NotNull(message = "Location Id is required")
	private Long locationId;
	
	@NotNull(message = "Race Ids is required")
	private Set<Long> raceIds;
	
	@NotNull(message = "Competitor Ids is required")
	private Set<Long> competitorIds;
}
