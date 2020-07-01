package com.example.car_rental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.car_rental.Common.Common;
import com.example.car_rental.Interface.ItemClickListener;
import com.example.car_rental.Model.Car;
import com.example.car_rental.ViewHolder.CarViewHolder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CarList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference carList;

    String categoryID = "";

    FirebaseRecyclerAdapter<Car, CarViewHolder> adapter;
    
    //Search Functionality
    FirebaseRecyclerAdapter<Car, CarViewHolder> searchAdpter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    
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
            if(Common.isConnectedToInternet(getBaseContext()))
                loadListCar(categoryID);
            else {
                /*Toast.makeText(CarList.this,"Please check you internet connection!",Toast.LENGTH_SHORT).show();
                return;*/

                    final Snackbar snackbar = Snackbar.make(recyclerView, "Please Check your internet conection", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                   loadListCar(categoryID);
                                }
                            });
                    snackbar.show();

            }

        }
        
        //Search
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter your car");
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Change suggest list when a user types some text
                List<String> suggest = new ArrayList<>();
                for(String search:suggestList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                    {
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //When Search Bar is clesed
                //Restore original adapter
                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //When Search is done
                //Show the result
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        
    }

    private void startSearch(CharSequence text) {
        searchAdpter = new FirebaseRecyclerAdapter<Car, CarViewHolder>(
                Car.class,
                R.layout.car_item,
                CarViewHolder.class,
                carList.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(CarViewHolder carViewHolder, Car car, int i) {
                carViewHolder.car_name.setText(car.getName());
                Picasso.with(getBaseContext()).load(car.getImage()).into(carViewHolder.car_image);

                final Car local = car;

                carViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start new Activity
                        Intent carDetail = new Intent(CarList.this,CarDetail.class);
                        carDetail.putExtra("CarId",searchAdpter.getRef(position).getKey());
                        startActivity(carDetail);



                    }
                });
            }

        };
        recyclerView.setAdapter(searchAdpter);
    }

    private void loadSuggest() {
        carList.orderByChild(String.valueOf("CatalogID".equals(categoryID))).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot:snapshot.getChildren())
                {
                    Car item = postSnapshot.getValue(Car.class);
                    suggestList.add(item.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadListCar(String categoryID)
    {
        adapter = new FirebaseRecyclerAdapter<Car,CarViewHolder>(
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
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start new Activity
                        Intent carDetail = new Intent(CarList.this,CarDetail.class);
                        carDetail.putExtra("CarId",adapter.getRef(position).getKey());
                        startActivity(carDetail);



                    }
                });
            }
        };

        //set adapter
        recyclerView.setAdapter(adapter);
    }
}