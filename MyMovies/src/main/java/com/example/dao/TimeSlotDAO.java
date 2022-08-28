package com.example.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.pojo.TimeSlot;
import com.example.repository.TimeSlotRepository;

@Component
public class TimeSlotDAO {
	@Autowired
	TimeSlotRepository repo;
	public TimeSlot findById(int id) {
		return (TimeSlot) repo.findById(id).orElse(null);
	}
	public TimeSlot insert( TimeSlot t) {
		return (TimeSlot) repo.save(t);
	}
	public List<TimeSlot> getAllTimeSlot() {
		return repo.findAll();
	}
	public String deleteById(int id) {
		repo.deleteById(id);
		return "deleted the id value:"+id; 
	}
	public TimeSlot updateTimeSlot(int id, TimeSlot t) {
		TimeSlot existingTimeSlot= repo.findById(id).orElse(null);
		existingTimeSlot.setScreen(t.getScreen());
		existingTimeSlot.setStartTiming(t.getStartTiming());
		existingTimeSlot.setEndTiming(t.getEndTiming());
		return (TimeSlot) repo.save(existingTimeSlot);
	}
}
