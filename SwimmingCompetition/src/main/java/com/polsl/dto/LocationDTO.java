package com.polsl.dto;

import java.util.Set;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO{
	private String name;
	private String address;
	private int poolLength;
	private int capacity;
	private Set<Long> competitionId;
}
