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
public class RacePostDTO{
	@NotNull(message = "Style is required")
	private RaceStyle style;
	
	@NotNull(message = "Distance is required")
	private Integer distance;
	
	@NotNull(message = "Date is required")
	private Timestamp date;
	
	private Set<Long> resultIds;
	
	private Long competitionId;
}
