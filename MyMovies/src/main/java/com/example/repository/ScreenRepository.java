package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pojo.Movies;
import com.example.pojo.Screen;
@Repository

public interface ScreenRepository extends JpaRepository <Screen,Integer>{
	
	public Screen findByScreenId(int id);
}
