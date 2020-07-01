package com.example.car_rental.Model;

public class User
{
    private String Username;
    private String Name;
    private String Password;
    private String Phone;



    public User() {
    }

    public User(String name, String password, String phone) {

        Name = name;
        Password = password;
        Phone = phone;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
