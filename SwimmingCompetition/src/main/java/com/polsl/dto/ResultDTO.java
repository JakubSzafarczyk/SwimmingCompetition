package com.polsl.dto;

import lombok.*;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO{
	private Long place;
	private Time time;
	private Long competitorId;
	private Long raceId;
}
