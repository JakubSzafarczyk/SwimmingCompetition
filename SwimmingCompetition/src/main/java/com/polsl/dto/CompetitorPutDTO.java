package com.polsl.dto;

import com.polsl.model.Gender;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitorPutDTO{
	@NotNull(message = "End Date is required")
	private String firstName;
	
	@NotNull(message = "Second Name is required")
	private String secondName;
	
	@NotNull(message = "Last Name is required")
	private String lastName;
	
	@NotNull(message = "Date Of Birth is required")
	private Date dateOfBirth;
	
	@NotNull(message = "Gender is required")
	private Gender gender;
	
	@NotNull(message = "Nationality is required")
	private String nationality;
	
	@NotNull(message = "Team Id is required")
	private Long teamId;
	
	@NotNull(message = "Result Ids is required")
	private Set<Long> resultIds;
	
	@NotNull(message = "Competition Ids is required")
	private Set<Long> competitionIds;
}

