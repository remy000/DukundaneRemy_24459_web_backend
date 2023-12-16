package com.example.realestate.config;

import com.example.realestate.domain.Users;
import com.example.realestate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepo.findByEmail(username);
        user.orElseThrow(() ->  new UsernameNotFoundException("User not found with username " + username));
        return user.map(UserInfoDetails::new).get();
    }
}
