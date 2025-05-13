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
public class CompetitorPostDTO{
	@NotNull(message = "End Date is required")
	private String firstName;
	
	private String secondName;
	
	@NotNull(message = "Last Name is required")
	private String lastName;
	
	@NotNull(message = "Date Of Birth is required")
	private Date dateOfBirth;
	
	@NotNull(message = "Gender is required")
	private Gender gender;
	
	@NotNull(message = "Nationality is required")
	private String nationality;
	
	private Long teamId;
	
	private Set<Long> resultIds;
	
	private Set<Long> competitionIds;
}

