package com.example.realestate.repository;

import com.example.realestate.domain.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Bookings,Integer> {
}
