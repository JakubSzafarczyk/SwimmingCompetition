package com.polsl.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Team {
	
	@Id
    @GeneratedValue
    private Long teamId;

    private String name;
    private String headquarters;
}
