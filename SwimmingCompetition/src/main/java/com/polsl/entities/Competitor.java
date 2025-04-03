package com.polsl.entities;

import java.sql.Date;
import com.polsl.model.Gender;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Competitor {
	@Id
	@GeneratedValue
	private long competitorId;
	
	private String firstName;
	private String secondName;
	private String lastName;
	private Date dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String nationality;
}
