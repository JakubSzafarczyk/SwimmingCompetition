package com.polsl.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionDTO {
	private String name;
	private Timestamp startDate;
	private Timestamp endDate;
	private String description;
	private Long locationId;
	private Set<Long> raceIds;
	private Set<Long> competitorIds;
}
