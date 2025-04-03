package com.polsl.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Location {

	@Id
	@GeneratedValue
	private long locationId;
	
	private long name;
	private String adderss;
	private int poolLength;
	private int capacity;
}
