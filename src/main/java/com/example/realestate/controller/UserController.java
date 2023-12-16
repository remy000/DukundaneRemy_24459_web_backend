package com.example.realestate.controller;

import com.example.realestate.domain.AuthRequest;
import com.example.realestate.domain.Users;
import com.example.realestate.service.EmailService;
import com.example.realestate.service.JwtService;
import com.example.realestate.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private UsersService usersService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private EmailService emailService;
    @Autowired

    public UserController(UsersService usersService, AuthenticationManager authenticationManager, JwtService jwtService, EmailService emailService) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    @PostMapping(value="/register")
        public ResponseEntity<?> saveUser(@RequestBody Users users) {
            if (users != null) {
                Users user=usersService.findUserbyemail(users.getEmail());
                if(user!=null){
                    return new ResponseEntity<>("Email is taken",HttpStatus.BAD_REQUEST);
                }
                else {
                    usersService.saveUser(users);
                    String subject = "Welcome to our platform";
                    String text = "Thank you for Signing up, your account has been created successfully";

                    String userEmail = users.getEmail();
                    if (userEmail != null) {
                        emailService.sendingEmails(userEmail, subject, text);
                        return new ResponseEntity<>("User saved and email sent", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("User email is null", HttpStatus.BAD_REQUEST);
                    }
                }

            }
            return new ResponseEntity<>("User is null", HttpStatus.BAD_REQUEST);
        }
        @GetMapping("/allUsers")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<?>allUsers(){
            List<Users>allUsers=usersService.allUsers();
            if(allUsers!=null) {
                return new ResponseEntity<>(allUsers, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }


    @PostMapping("/authentication")
    public ResponseEntity<?> authReqAndToken(@RequestBody AuthRequest auth){
        Users users=usersService.findUserbyemail(auth.getUsername());
        if(users!=null) {
            String roles = users.getRoles();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(auth.getUsername(), roles);
                Map<String, String> response = new HashMap<>();
                response.put("token", token);

                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                throw new UsernameNotFoundException("no user found");
            }
        }
        else{
            throw new RuntimeException("User is null");
        }


    }


}
