package com.polsl.dto;

import lombok.*;

import java.sql.Time;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultPostDTO{
	private Long place;
	
	@NotNull(message = "Time is required")
	private Time time;
	
	@NotNull(message = "Competitor Id is required")
	private Long competitorId;
	
	@NotNull(message = "Race Id is required")
	private Long raceId;
}
