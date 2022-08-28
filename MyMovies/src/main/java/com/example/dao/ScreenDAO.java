package com.example.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.pojo.Movies;
import com.example.pojo.Screen;
import com.example.pojo.Theater;
import com.example.repository.ScreenRepository;

@Component
public class ScreenDAO {
	@Autowired
	ScreenRepository repo;
	public Screen insert(Screen s) {
		return (Screen) repo.save(s);
	}
	public Screen findById(int id) {
		return (Screen) repo.findById(id).orElse(null);
	}
	public List<Screen> getAll() {
		return repo.findAll();
	}
	public String deleteById(int id) {
		repo.deleteById(id);
		return "deleted the screenid value:"+id; 
	}
	public Screen update(Screen s) {
		Screen existingScreen=(Screen) repo.findByScreenId(s.getScreenId());
		existingScreen.setMovie(s.getMovie());
		existingScreen.setScreenName(s.getScreenName());
		existingScreen.setTheater(s.getTheater());
		existingScreen.setTotalNoOfSeats(s.getTotalNoOfSeats());
		existingScreen.setAvailableNoOfSeats(s.getAvailableNoOfSeats());
		return (Screen) repo.save(existingScreen);
	}
}
