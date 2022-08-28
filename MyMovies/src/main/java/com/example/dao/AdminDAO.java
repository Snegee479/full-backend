package com.example.dao;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.pojo.Admin;
import com.example.pojo.Movies;
import com.example.repository.AdminRepository;

@Component
public class AdminDAO {
	
	@Autowired
	AdminRepository repo;
	public Admin insert(Admin a){
		return (Admin) repo.save(a);
	}
	public Admin findByPassword(String password) {
		return repo.findByPassword(password);
	}
	public Admin findByEmail(String email) {
		System.out.println("email is "+email);
		System.out.println(" value obtained from repo is "+repo.findByEmail(email));
		return (Admin) repo.findByEmail(email);
		
	}
}
