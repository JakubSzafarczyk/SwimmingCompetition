package com.polsl.dto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamPutDTO{
	@NotNull(message = "Name is required")
	private String name;
	
	@NotNull(message = "Headquarters is required")
	private String headquarters;
	
	@NotNull(message = "Coach Id is required")
	private Set<Long> coachId;
	
	@NotNull(message = "Competitor Id is required")
	private Set<Long> competitorId;
}
