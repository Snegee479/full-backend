package com.example.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.pojo.Movies;
import com.example.repository.MoviesRepository;


@Service
public class MoviesDAO {
	
	@Autowired
	MoviesRepository repo;
	
	public Movies insert(Movies m){
		return (Movies) repo.save(m);
	}
	public Movies findByMovieId(int id) {
		return (Movies)repo.findByMovieId(id);
	}
	public List<Movies> getAll() {
		return repo.findAll();
	}
	public String deleteById(int id) {
		repo.deleteById(id);
		return "deleted the id value:"+id; 
	}
	public Movies update(int id,Movies m) {
		Movies existingMovie=(Movies) repo.findByMovieId(id);
		existingMovie.setActiveStatus(m.getActiveStatus());
		existingMovie.setLanguage(m.getLanguage());
		existingMovie.setMovieDescription(m.getMovieDescription());
		existingMovie.setTicketPrice(m.getTicketPrice());
		existingMovie.setCategory(m.getCategory());
		existingMovie.setMovieName(m.getMovieName());
		return (Movies) repo.save(existingMovie);
	}
	public Movies findByMovieName(String movieName) {
		
		return repo.findByMovieName(movieName);
	}
	
}
