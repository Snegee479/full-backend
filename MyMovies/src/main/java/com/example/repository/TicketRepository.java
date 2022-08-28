package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.pojo.Ticket;
@Repository

public interface TicketRepository extends JpaRepository <Ticket,Integer>{

}
