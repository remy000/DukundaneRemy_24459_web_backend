package com.example.realestate.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bid;
    @OneToOne
    @JoinColumn(name = "id")
    @JsonBackReference("HouseBookingReference")
    private House house;
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference("UserBookingReference")
    private Users user;

    public Bookings() {
    }

    public Bookings(int bid, House house, Users user) {
        this.bid = bid;
        this.house = house;
        this.user = user;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
