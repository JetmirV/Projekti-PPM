package com.example.car_rental.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.car_rental.Model.Reservation;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper
{
    private static final String DB_NAME = "car_rentalDB.db";
    private static final int DB_VER = 1;

    public Database(Context context)   {
        super(context, DB_NAME,null, DB_VER);
    }

    public List<Reservation> getCarts()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"CarID","CarName","Quantity","Price"};
        String sqlTable = "ReservationDetail";

        qb.setTables(sqlTable);

        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Reservation> result = new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                result.add(new Reservation(c.getString(c.getColumnIndex("CarID")),
                        c.getString(c.getColumnIndex("CarName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price"))));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Reservation reservation)
    {
        SQLiteDatabase db = getReadableDatabase();
        /*String query = String.format("INSERT INTO ReservationDetail(CarID,CarName,Quantity,Price) VALUES('%1&s','%2&s','%3&s','%4&s');",
                reservation.getCarID(),
                reservation.getCarName(),
                reservation.getQuantity(),
                reservation.getPrice());*/

        String query = String.format("INSERT INTO ReservationDetail(CarID,CarName,Quantity,Price) VALUES('" + reservation.getCarID() + "','" + reservation.getCarName() + "','" + reservation.getQuantity() + "','" + reservation.getPrice() + "');");
        db.execSQL(query);
    }

    public void cleanCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM ReservationDetail");
        db.execSQL(query);
    }
}
