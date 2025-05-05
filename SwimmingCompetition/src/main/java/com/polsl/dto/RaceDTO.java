package com.polsl.dto;

import com.polsl.model.RaceStyle;

import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RaceDTO{
	private RaceStyle style;
	private int distance;
	private Timestamp date;
	private Set<Long> resultIds;
	private Long competitionId;
}
