package com.polsl.entity;

import java.util.Set;
import java.util.HashSet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", nullable = false)
    private Long teamId;

	@NotNull
	@Column(name = "name", nullable = false, length = 100)
    private String name;
	
	@Column(name = "headquarters", length = 100)
    private String headquarters;
	
	@OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Coach> coaches = new HashSet<Coach>();
	
	@OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Competitor> competitors = new HashSet<Competitor>();
}
