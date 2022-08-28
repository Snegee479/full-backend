package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pojo.Theater;

public interface TheaterRepository extends JpaRepository <Theater,Integer>{
	public Theater findByTheaterName(String name);
}
