package com.polsl.entity;

import java.sql.Date;
import com.polsl.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "coaches")
public class Coach {
	@Id
	@NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_id", nullable = false)
	private Long coachId;
	
	@NotNull
	@Column(name = "first_name", nullable = false, length = 20)
	private String firstName;
	
	@Column(name = "second_name", length = 20)
	private String secondName;
	
	@NotNull
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;
	
	@NotNull
	@Column(name = "date_of_birth", nullable = false)
	private Date dateOfBirth;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "gender", nullable = false, length = 10)
	private Gender gender;
	
	@NotNull
	@Column(name = "nationality", nullable = false, length = 50)
	private String nationality;
	
	@ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
