package com.example.realestate.repository;

import com.example.realestate.domain.House;
import com.example.realestate.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRepo extends JpaRepository<House,Integer> {
    Optional<House> findByLocation(String location);
}
