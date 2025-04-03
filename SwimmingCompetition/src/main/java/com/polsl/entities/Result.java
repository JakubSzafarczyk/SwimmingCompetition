package com.polsl.entities;


import java.sql.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Result {

	@Id
	@GeneratedValue
	private long resultId;
	
	private long place;
	private Timestamp time;
	
	
}
