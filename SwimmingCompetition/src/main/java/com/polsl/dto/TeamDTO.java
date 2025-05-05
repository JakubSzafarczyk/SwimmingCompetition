package com.polsl.dto;

import java.util.Set;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO{
	private String name;
	private String headquarters;
	private Set<Long> coachId;
	private Set<Long> competitorId;
}
