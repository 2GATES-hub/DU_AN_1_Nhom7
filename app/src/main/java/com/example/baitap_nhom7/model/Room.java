package com.example.roomrental.model;

public class Room {
    private String id;
    private String name;
    private double price;
    private String status; // "Còn trống" hoặc "Đã thuê"
    private String renterName;
    private String phone;

    public Room(String id, String name, double price, String status, String renterName, String phone) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.renterName = renterName;
        this.phone = phone;
    }

    // Getters và Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRenterName() { return renterName; }
    public void setRenterName(String renterName) { this.renterName = renterName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}