package com.polsl.entities;

import java.sql.Date;
import com.polsl.model.Gender;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "competitors")
public class Competitor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competitorId", nullable = false)
	private long competitorId;
	
	@Column(name = "firstName", nullable = false, length = 20)
	private String firstName;
	
	@Column(name = "secondName", length = 20)
	private String secondName;
	
	@Column(name = "lastName", nullable = false, length = 50)
	private String lastName;
	
	 @Column(name = "dateOfBirth", nullable = false)
	private Date dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender", nullable = false, length = 10)
	private Gender gender;
	
	@Column(name = "nationality", nullable = false, length = 50)
	private String nationality;
}
