package com.polsl.dto;

import com.polsl.model.RaceStyle;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RacePutDTO{
	@NotNull(message = "Style is required")
	private RaceStyle style;
	
	@NotNull(message = "Distance is required")
	private Integer distance;
	
	@NotNull(message = "Date is required")
	private Timestamp date;
	
	@NotNull(message = "Result Ids is required")
	private Set<Long> resultIds;
	
	@NotNull(message = "Competition Id is required")
	private Long competitionId;
}
