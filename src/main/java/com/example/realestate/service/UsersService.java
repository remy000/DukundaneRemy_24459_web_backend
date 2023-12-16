package com.example.realestate.service;

import com.example.realestate.domain.Users;
import com.example.realestate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersService {
    private UserRepo userRepo;
    private PasswordEncoder encoder;

 @Autowired
    public UsersService(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public Users saveUser(Users user) {
        if (user != null && user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
            return user;
        }
        return null;
    }
     public List<Users>allUsers(){
         return userRepo.findAll();
     }
     public Users findUser(int id){
         return userRepo.findById(id).orElse(null);
     }
     public boolean deleteUser(int id){
         Users user=userRepo.findById(id).orElse(null);
         if(user!=null){
             userRepo.delete(user);
             return true;
         }
         return false;
     }
     public String updateUser(int id,String name,String password,String email){
         Users users=userRepo.findById(id).orElse(null);
         if (users!=null){
             users.setNames(name);
             users.setEmail(email);
             String hashedPassword=encoder.encode(password);
             users.setPassword(hashedPassword);
             return "Users updated";

         }
         return "user not updated";
     }
     public Users findUserbyemail(String email){
         return userRepo.findByEmail(email).orElse(null);
     }


}
