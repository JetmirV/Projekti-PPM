package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.car_rental.Model.Car;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CarDetail extends AppCompatActivity {

    TextView car_name,car_price,car_description;
    ImageView car_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String carId="";

    FirebaseDatabase database;
    DatabaseReference cars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        //Firebase

        database = FirebaseDatabase.getInstance();
        cars = database.getReference("Cars");

        //Init view

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);

        car_description = (TextView)findViewById(R.id.car_description);
        car_name = (TextView)findViewById(R.id.car_name);
        car_price = (TextView)findViewById(R.id.car_price);
        car_image = (ImageView)findViewById(R.id.img_car);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        //Get food Id from Intent

        if(getIntent()!=null)
            carId = getIntent().getStringExtra("CarsId");

        if(!carId.isEmpty()){
            getDetailCar(carId);
        }
    }

    private void getDetailCar(String carId) {
        cars.child(carId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            Car car = dataSnapshot.getValue(Car.class);

            //set image

                Picasso.with(getBaseContext()).load(car.getImage())
                        .into(car_image);

                collapsingToolbarLayout.setTitle(car.getName());

                car_price.setText(car.getPrice());

                car_name.setText(car.getName());

                car_description.setText(car.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}