package com.polsl.dto;

import com.polsl.model.Gender;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoachDTO {
	private String firstName;
	private String secondName;
	private String lastName;
	private Date dateOfBirth;
	private Gender gender;
	private String nationality;
	private Long teamId;
}
