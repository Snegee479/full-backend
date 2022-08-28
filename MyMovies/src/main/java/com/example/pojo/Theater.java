package com.example.pojo;



	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import lombok.Data;

@Data
@Entity
public class Theater {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int theaterId;
	String theaterName;
	
}
