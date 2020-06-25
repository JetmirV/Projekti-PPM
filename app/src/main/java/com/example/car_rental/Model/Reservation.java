package com.example.car_rental.Model;

public class Reservation {

    private String CarID;
    private String CarName;
    private String Quantity;
    private String Price;

    public Reservation() {
    }

    public Reservation(String carID, String carName, String quantity, String price) {
        CarID = carID;
        CarName = carName;
        Quantity = quantity;
        Price = price;
    }

    public String getCarID() {
        return CarID;
    }

    public void setCarID(String carID) {
        CarID = carID;
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
