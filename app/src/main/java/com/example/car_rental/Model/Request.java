package com.example.car_rental.Model;

import java.util.List;

public class Request {
    private  String phone;
    private  String name;
   // private  String address;
    private  String total;
    private  String status;
    private  List<Reservation> cars; //list of car order;

    public Request() {
    }

    public Request(String phone, String name, /*String address,*/ String total, List<Reservation> cars) {
        this.phone = phone;
        this.name = name;
        //this.address = address;
        this.total = total;
        this.cars = cars;
        this.status = "0";//Default is 0, 0:placed ,1: Shipping,2:Shipped
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }*/

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Reservation> getCars() {
        return cars;
    }

    public void setCars(List<Reservation> cars) {
        this.cars = cars;
    }
}
