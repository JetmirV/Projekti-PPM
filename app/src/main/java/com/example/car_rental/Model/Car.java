package com.example.car_rental.Model;

public class Car
{
    private String Name, Image, Description, Price, CategoryID;

    public Car() {
    }

    public Car(String name, String image, String description, String price, String categoryID) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        CategoryID = categoryID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
}
