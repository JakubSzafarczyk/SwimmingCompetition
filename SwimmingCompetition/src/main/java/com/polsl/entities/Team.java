package com.polsl.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamId")
    private Long teamId;

	@Column(name = "name", nullable = false, length = 100)
    private String name;
	
	@Column(name = "headquarters", length = 100)
    private String headquarters;
}
