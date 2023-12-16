package com.example.realestate.controller;


import com.example.realestate.domain.BookingDetails;
import com.example.realestate.domain.Bookings;
import com.example.realestate.domain.House;
import com.example.realestate.domain.Users;
import com.example.realestate.service.BookingService;
import com.example.realestate.service.EmailService;
import com.example.realestate.service.HouseService;
import com.example.realestate.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private UsersService usersService;
    private HouseService houseService;
    private BookingService bookingService;

    private EmailService emailService;
    @Autowired

    public BookingController(UsersService usersService, HouseService houseService, BookingService bookingService, EmailService emailService) {
        this.usersService = usersService;
        this.houseService = houseService;
        this.bookingService = bookingService;
        this.emailService = emailService;
    }
    @PostMapping("/saveBook")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?>saveBooking(@RequestParam String email, @RequestParam Integer id){
        Users user=usersService.findUserbyemail(email);
        House house=houseService.findHouse(id);
        if(house!=null){
            house.setAvailability("booked");
            houseService.updateHouse(house);

        }
        Bookings book=new Bookings();
        book.setHouse(house);
        book.setUser(user);
        bookingService.saveBooking(book);
        String subject = "Booking";
        String text = "Thank you for booking House, you can come to visit anytime you want";
        emailService.sendingEmails(email, subject, text);
        return new ResponseEntity<>("booked", HttpStatus.OK);
    }
    @GetMapping("/allBookings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>allBookings(){
        List<Bookings>allBookings=bookingService.allBookings();
        if(allBookings!=null) {
            List<BookingDetails>bookingDetails=new ArrayList<>();
            for(Bookings book:allBookings){
                int bkid=book.getBid();
                String names=book.getUser().getNames();
                String email=book.getUser().getEmail();
                String houseType=book.getHouse().getHouseType();
                String location=book.getHouse().getLocation();
                int houseId=book.getHouse().getId();
                String status=book.getHouse().getStatus();
                BookingDetails books=new BookingDetails(bkid,names,email,houseId,location,houseType,status);
                bookingDetails.add(books);
            }
            return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
    @DeleteMapping("/deleteBooking/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>deleteBooking(@PathVariable("id") Integer id) {
        Bookings book = bookingService.findBooking(id);
        if (book != null) {
            House house = houseService.findHouse(book.getHouse().getId());
            if (house != null) {
                house.setAvailability("free");
                boolean deleted = bookingService.deleteBooking(id);
                if (deleted) {
                    return new ResponseEntity<>("Booking cancelled", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("booking not cancelled", HttpStatus.BAD_REQUEST);

                }
            } else {
                return new ResponseEntity<>("there is no booking", HttpStatus.BAD_REQUEST);


            }
        }
        return new ResponseEntity<>("there is no booking", HttpStatus.BAD_REQUEST);
    }

}
