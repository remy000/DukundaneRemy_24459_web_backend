package com.example.realestate.domain;

public class BookingDetails {
    private int bkid;
    private String names;
    private String email;
    private int houseid;
    private String houseType;
    private String location;
    private String status;

    public BookingDetails() {
    }

    public BookingDetails(int bkid, String names, String email, int houseid, String houseType, String location, String status) {
        this.bkid = bkid;
        this.names = names;
        this.email = email;
        this.houseid = houseid;
        this.houseType = houseType;
        this.location = location;
        this.status = status;
    }

    public int getBkid() {
        return bkid;
    }

    public void setBkid(int bkid) {
        this.bkid = bkid;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getHouseid() {
        return houseid;
    }

    public void setHouseid(int houseid) {
        this.houseid = houseid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
