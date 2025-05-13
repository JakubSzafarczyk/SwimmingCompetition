package com.polsl.dto;

import com.polsl.model.Gender;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoachPutDTO {
	@NotNull(message = "First Name is required")
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
}
