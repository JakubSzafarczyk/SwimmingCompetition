package com.polsl.dto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationPutDTO{
	@NotNull(message = "Name is required")
	private String name;
	
	@NotNull(message = "Address is required")
	private String address;
	
	@NotNull(message = "Pool Length is required")
	private Integer poolLength;
	
	@NotNull(message = "Capacity is required")
	private Integer capacity;
	
	@NotNull(message = "Competition Id is required")
	private Set<Long> competitionId;
}
