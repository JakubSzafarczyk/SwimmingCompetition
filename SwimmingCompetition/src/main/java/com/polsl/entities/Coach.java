package com.polsl.entities;

import java.sql.Date;
import com.polsl.model.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "coaches")
public class Coach {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coachId")
	private long coachID;
	
	@Column(name = "firstName", nullable = false, length = 20)
	private String firstName;
	
	@Column(name = "secondName", length = 20)
	private String secondName;
	
	@Column(name = "lastName", nullable = false, length = 50)
	private String lastName;
	
	 @Column(name = "dateOfBirth", nullable = false)
	private Date dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender", length = 10, nullable = false)
	private Gender gender;
	
	@Column(name = "nationality", length = 50, nullable = false)
	private String nationality;
}
