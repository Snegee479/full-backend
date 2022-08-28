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
public class TimeSlot {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	int timeId;
	String startTiming;
	String endTiming;
	@ManyToOne(cascade = CascadeType.ALL)  
	@JoinColumn(name="screenId")  
	Screen screen;
	
}
