package com.polsl.entities;

import java.sql.Date;
import com.polsl.model.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Coach {
	@Id
	@GeneratedValue
	private long coachID;
	
	private String  firstName;
	private String  secondName;
	private String  lastName;
	private Date dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String nationality;
}
