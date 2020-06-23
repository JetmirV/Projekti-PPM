package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.car_rental.Interface.ItemClickListener;
import com.example.car_rental.Model.Car;
import com.example.car_rental.ViewHolder.CarViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CarList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference carList;

    String categoryID = "";

    FirebaseRecyclerAdapter<Car, CarViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        carList = database.getReference("Cars");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_car);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get intent here
        if(getIntent() != null)
        {
            categoryID = getIntent().getStringExtra("CategoryID");
        }
        if(!categoryID.isEmpty() && categoryID != null)
        {
            loadListCar(categoryID);
        }
    }

    private void loadListCar(String categoryID)
    {
        adapter = new FirebaseRecyclerAdapter<Car, CarViewHolder>(
                Car.class,
                R.layout.car_item,
                CarViewHolder.class,
                carList.orderByChild("CategoryID").equalTo(categoryID)
                ) {
            @Override
            protected void populateViewHolder(CarViewHolder carViewHolder, Car car, int i) {
                carViewHolder.car_name.setText(car.getName());
                Picasso.with(getBaseContext()).load(car.getImage()).into(carViewHolder.car_image);

                final Car local = car;

                carViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int postion, boolean isLongClick) {
                        Toast.makeText(CarList.this, "" + local.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        //set adapter
        recyclerView.setAdapter(adapter);
    }
}