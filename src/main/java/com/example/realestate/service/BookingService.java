package com.example.realestate.service;

import com.example.realestate.domain.Bookings;
import com.example.realestate.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;

    public Bookings saveBooking(Bookings bookings){
        if(bookings!=null){
            bookingRepo.save(bookings);
            return bookings;
        }
        return null;
    }
    public List<Bookings> allBookings(){
        return bookingRepo.findAll();

    }
    public Bookings findBooking(int id){
        return bookingRepo.findById(id).orElse(null);
    }
    public boolean deleteBooking(int id){
        Bookings book=bookingRepo.findById(id).orElse(null);
        if(book!=null){
            bookingRepo.delete(book);
            return true;
        }
        return false;
    }
    public Bookings updateBooking(Bookings book){
        if(book!=null){
            Bookings booking=bookingRepo.findById(book.getBid()).orElse(null);
            if(booking!=null){
                booking.setHouse(book.getHouse());
                booking.setUser(book.getUser());
                return booking;
            }
        }
        return null;
    }
}
