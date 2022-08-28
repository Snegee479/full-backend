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

	public class Ticket {

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		int ticketId;
		int ticketCount;//2 //FRONT END DATA
		double ticketPrice;//150.00 //FRONT END DATA FROM MOVIE OBJECT From timeslot obj's screen obj
		@ManyToOne(cascade = CascadeType.ALL)  
		@JoinColumn(name="timeId")  
		TimeSlot timeSlot;  //FRONT END DATA 

		//timeslot //screen id 
		//screen //theater and movie
		double internetFee;//104.00
		double  totalFee;//404.00	
		
		
//		Admin admin; //FRONT END DATA FOR CUSTOMER DETAIL

		
		
		
	}

	
	
	
	
	
	
	



