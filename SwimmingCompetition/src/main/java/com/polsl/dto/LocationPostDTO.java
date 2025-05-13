package com.polsl.dto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationPostDTO{
	@NotNull(message = "Name is required")
	private String name;
	
	@NotNull(message = "Address is required")
	private String address;
	
	@NotNull(message = "Pool Length is required")
	private Integer poolLength;
	
	private Integer capacity;
	
	private Set<Long> competitionId;
}
