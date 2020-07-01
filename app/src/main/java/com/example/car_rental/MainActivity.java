package com.example.car_rental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.car_rental.Common.Common;
import com.example.car_rental.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button btnSignUp, btnSignIn;
    TextView txtSlogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        //Init Paper
        Paper.init(this);




        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(),SignUp.class);
                startActivity(signUp);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(getApplicationContext(),SignIn.class);
                startActivity(signIn);
            }
        });

        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if(user != null && pwd != null)
        {
            if(!user.isEmpty() && !pwd.isEmpty())
                login(user,pwd);
        }


    }

    private void login(final String username, final String pwd) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        if (Common.isConnectedToInternet(getBaseContext())) {



            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Please wating......");
            mDialog.show();
            table_user.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //check if user exists in database
                    if (dataSnapshot.child(username).exists()) {
                        //Get USer Information
                        mDialog.dismiss();
                        User user = dataSnapshot.child(username).getValue(User.class);
                        user.setPhone(username);
                        if (user.getPassword().equals(pwd)) {
                            {
                                Intent homeIntent = new Intent(MainActivity.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Sign In not Succsesfull !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "User doesen't exist in database !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else
        {
            Toast.makeText(MainActivity.this,"Please check you internet connection!",Toast.LENGTH_SHORT).show();
            return;
        }
    }




}
