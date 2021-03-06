package com.example.car_rental;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.car_rental.Common.Common;
import com.example.car_rental.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
    MaterialEditText editPhone,editPassword,editName,editUsername;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editPhone = (MaterialEditText)findViewById(R.id.editPhone);
        editName = (MaterialEditText)findViewById(R.id.editName);
        editPassword = (MaterialEditText)findViewById(R.id.editPassword);
        editUsername = (MaterialEditText)findViewById(R.id.editUsername);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        //Initialize firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isConnectedToInternet(getBaseContext())) {

                    final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                    mDialog.setMessage("Please waiting......");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            //Validate the name input
                            if (!editName.getText().toString().equals(""))
                            {
                                if (!(editName.getText().toString()).matches(".*\\d.*"))
                                {
                                    if(!(editUsername.getText().toString()).equals(""))
                                    {
                                        if(!(editPhone.getText().toString()).equals(""))
                                        {
                                            if(!(editPassword.getText().toString()).equals(""))
                                            {
                                                if (dataSnapshot.child(editUsername.getText().toString().toLowerCase()).exists())
                                                {
                                                    mDialog.dismiss();
                                                    Toast.makeText(SignUp.this, "Username already exists !", Toast.LENGTH_SHORT).show();
                                                } else
                                                {
                                                    mDialog.dismiss();
                                                    User user = new User(editName.getText().toString(), editPassword.getText().toString(), editPhone.getText().toString());
                                                    table_user.child(editUsername.getText().toString().toLowerCase()).setValue(user);
                                                    Toast.makeText(SignUp.this, "Sign Up succsesfull !", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            }else
                                            {
                                                mDialog.dismiss();
                                                Toast.makeText(SignUp.this, "Password field is empty!", Toast.LENGTH_SHORT).show();
                                            }
                                        }else
                                        {
                                            mDialog.dismiss();
                                            Toast.makeText(SignUp.this, "Phone Number field is empty!", Toast.LENGTH_SHORT).show();
                                        }

                                    }else
                                    {
                                        mDialog.dismiss();
                                        Toast.makeText(SignUp.this, "Username field is empty!", Toast.LENGTH_SHORT).show();
                                    }
                                } else
                                    {
                                    mDialog.dismiss();
                                    Toast.makeText(SignUp.this, "Name field contains numbers!", Toast.LENGTH_SHORT).show();
                                    }
                            } else
                                {
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "Name field is empty!", Toast.LENGTH_SHORT).show();
                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                } else {
                    Toast.makeText(SignUp.this, "Please check you internet connection!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

}