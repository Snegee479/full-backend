package com.example.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Data
@Entity
public class Movies {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int movieId;
	@Column(unique = true)
	String movieName;
	Boolean activeStatus;
	double ticketPrice;
	String language;
	String movieDescription;
	String category;
	@Lob
    @Column(name = "pic")
    private byte[] pic;

	
	}
