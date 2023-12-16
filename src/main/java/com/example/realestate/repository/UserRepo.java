package com.example.realestate.repository;

import com.example.realestate.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {

    Optional<Users> findByEmail(String email);
}
