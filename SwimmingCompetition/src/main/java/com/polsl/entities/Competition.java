package com.polsl.entities;

import java.sql.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Competition {
	@Id
	@GeneratedValue
	private long competitionId;
	
	private String name;
	private Timestamp startDate;
	private Timestamp endDate;
	private String description;
}
