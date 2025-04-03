package com.polsl.entities;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
public class Competitor {

	@Id
	long competitorId;
	String  firstName;
	String  secondName;
	String  lastName;
	Date dateOfBirth;
	// gender;
	String nationality;
}
