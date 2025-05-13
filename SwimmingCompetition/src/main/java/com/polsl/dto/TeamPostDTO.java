package com.polsl.dto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamPostDTO{
	@NotNull(message = "Name is required")
	private String name;
	
	private String headquarters;
	
	private Set<Long> coachId;
	
	private Set<Long> competitorId;
}
