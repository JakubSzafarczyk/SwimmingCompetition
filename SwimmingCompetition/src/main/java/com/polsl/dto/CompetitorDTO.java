package com.polsl.dto;

import com.polsl.model.Gender;

import lombok.*;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitorDTO{
	private String firstName;
	private String secondName;
	private String lastName;
	private Date dateOfBirth;
	private Gender gender;
	private String nationality;
	private Long teamId;
	private Set<Long> resultIds;
	private Set<Long> competitionIds;
}

