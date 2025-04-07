package com.polsl.entity;

import java.sql.Date;
import java.util.Set;
import java.util.HashSet;
import com.polsl.model.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "competitors")
public class Competitor {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competitor_id", nullable = false)
	private Long competitorId;
	
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
	
	@OneToMany(mappedBy = "competitor")
    private Set<Result> results = new HashSet<Result>();
	
	@ManyToMany
    @JoinTable(
        name = "competitor_competition",
        joinColumns = @JoinColumn(name = "competitor_id"),
        inverseJoinColumns = @JoinColumn(name = "competition_id")
    )
    private Set<Competition> competitions = new HashSet<Competition>();
}