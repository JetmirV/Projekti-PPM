package com.example.car_rental;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_rental.Common.Common;
import com.example.car_rental.Database.Database;
import com.example.car_rental.Model.Request;
import com.example.car_rental.Model.Reservation;
import com.example.car_rental.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import com.firebase.ui.database.paging.FirebaseDataSource;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnBook;

    List<Reservation> cart = new ArrayList<>();
    CartAdapter adapter;

    public static String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnBook = (Button)findViewById(R.id.btnBook);

        fetchData fetchData = new fetchData();
        fetchData.execute();

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.size() > 0)
                    showAlertDialog();
                else
                    Toast.makeText(Cart.this,  "Your cart is empty!", Toast.LENGTH_SHORT).show();

            }
        });

        loadListCar();

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Confirmation!");
        alertDialog.setMessage("Are you sure you want to make a reservation");



        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Create new request

                Request request = new Request(
                        Common.currentUser.getUsername(),
                        Common.currentUser.getName(),
                        txtTotalPrice.getText().toString(),
                        data,
                        cart

                );
                //Submit to Firebase
                //System.CurrentMilli to key
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                // Delete cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Thank you, Reservation processed! ", Toast.LENGTH_SHORT).show();
                Toast.makeText(Cart.this, data, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        alertDialog.show();

    }

    private void loadListCar()
    {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


        //Calculate total price
        int total = 0;
        for(Reservation reservation:cart) {
            if(reservation.getTimeType().toString().equals("Hours"))
                total += (Integer.parseInt((reservation.getPrice()).split(" ", 3)[0])) * (Integer.parseInt(reservation.getReservationTime()));
            else
                total += (Integer.parseInt((reservation.getPrice()).split(" ", 3)[0])) * 24 *(Integer.parseInt(reservation.getReservationTime()));
        }

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
        {
            deleteCart(item.getOrder());
        }
        return true;
    }

    private void deleteCart(int position) {
        cart.remove(position);
        //Delete all old data from SQLite
        new Database(this).cleanCart();

        for(Reservation item:cart)
            new Database(this).addToCart(item);

        loadListCar();
    }

    /*private String loadDate() {
        String data = "";
        String CurrentDate = "";
        try {
            URL url = new URL("http://worldtimeapi.org/api/timezone/Europe/Tirane");

            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while(line != null)
            {

                line = bufferedReader.readLine();
                data = data + line;
            }

            Gson gson = new Gson();
            JsonParser jsonParser = new JsonParser();


            JsonObject jsonObject = jsonParser.parse(data.replace("null","")).getAsJsonObject();
            CurrentDate = jsonObject.get("datetime").toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return CurrentDate + "\"";

    }*/

}