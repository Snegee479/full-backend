package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pojo.Admin;

@Repository

public interface AdminRepository extends JpaRepository<Admin,String> {
	public Admin findByPassword(String password);
	public Admin findByEmail(String email);
		
	}

