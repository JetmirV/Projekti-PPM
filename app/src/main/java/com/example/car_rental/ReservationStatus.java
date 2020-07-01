package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.car_rental.Common.Common;
import com.example.car_rental.Model.Request;
import com.example.car_rental.Model.Reservation;
import com.example.car_rental.ViewHolder.ReservationViewHolder;
import com.example.car_rental.Database.Database;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReservationStatus extends AppCompatActivity {


    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, ReservationViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_status);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView=(RecyclerView)findViewById(R.id.listReservations);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadReservations(Common.currentUser.getUsername());

    }
    private void loadReservations(String username){
        adapter = new FirebaseRecyclerAdapter<Request, ReservationViewHolder>(
                Request.class,
                R.layout.reservation_layout,
                ReservationViewHolder.class,
                requests.orderByChild("username").equalTo(username)
        ) {
            @Override
            protected void populateViewHolder(ReservationViewHolder viewHolder, Request model, int position) {
                viewHolder.txtReservationId.setText(adapter.getRef(position).getKey());
                viewHolder.txtReservationStatus.setText(convertCodeToStatus(model.getStatus()));
                //viewHolder.txtReservationAddress.setText(model.getAddress());
                viewHolder.txtReservationPhone.setText(model.getUsername());

            }
        };
        recyclerView.setAdapter(adapter);
    }


    private String convertCodeToStatus(String status){
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }
}