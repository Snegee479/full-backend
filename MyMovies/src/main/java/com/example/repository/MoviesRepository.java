package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.pojo.Movies;
@Repository
public interface MoviesRepository extends JpaRepository<Movies,Integer>{
	public Movies findByMovieId(int id);

	public Movies findByMovieName(String movieName);
}


