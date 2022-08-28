package com.example.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

	@Data
	@Entity
	public class Screen {
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		int screenId;
		String screenName;
		int totalNoOfSeats;
		int availableNoOfSeats;
		@ManyToOne(cascade = CascadeType.ALL)  
		@JoinColumn(name="theater_id")  
		Theater theater;
		@ManyToOne(cascade = CascadeType.ALL)  
		@JoinColumn(name="movie_id")  
		Movies movie;
		
	}
