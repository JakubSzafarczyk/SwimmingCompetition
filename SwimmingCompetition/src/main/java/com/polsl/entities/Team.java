package com.polsl.entities;

import javax.persistence.*;
import lombok.*;

@Entity
public class Team {
	@Id
    @GeneratedValue
    private Long teamId;

    private String name;
    private String headquarters;
}
