package com.example.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.pojo.Theater;
import com.example.repository.TheaterRepository;

@Component
public class TheaterDAO {
	@Autowired
	TheaterRepository repo;
	
	public Theater findByTheaterName(String name) {
		return (Theater) repo.findByTheaterName(name);
	}
	public Theater findById(int id) {
		return (Theater) repo.findById(id).orElse(null);
	}
	public Theater insert(Theater s) {
		return (Theater) repo.save(s);
	}
	public List<Theater> getAllTheater() {
		return repo.findAll();
	}
	public String deleteById(int id) {
		repo.deleteById(id);
		return "deleted the screenid value:"+id; 
	}
	public Theater update(int id, Theater t) {
		Theater existingTheater=(Theater) repo.findById(id).orElse(null);
		existingTheater.setTheaterName(t.getTheaterName());
		return (Theater) repo.save(existingTheater);
	}
}
