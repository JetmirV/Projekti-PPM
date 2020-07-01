package com.example.car_rental.Model;

public class Reservation {

    private String CarID;
    private String CarName;
    private String ReservationTime;
    private String Price;
    private String TimeType;

    public Reservation() {
    }

    public Reservation(String carID, String carName, String reservationTime, String price,String timeType) {
        CarID = carID;
        CarName = carName;
        ReservationTime = reservationTime;
        Price = price;
        TimeType = timeType;
    }

    public String getTimeType() {
        return TimeType;
    }

    public void setTimeType(String timeType) {
        TimeType = timeType;
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

    public String getReservationTime() {
        return ReservationTime;
    }

    public void setReservationTime(String reservationTime) {
        ReservationTime = reservationTime;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
