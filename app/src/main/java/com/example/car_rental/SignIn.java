package com.example.car_rental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.car_rental.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    EditText editPhone,editPassword;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPassword = (MaterialEditText)findViewById(R.id.editPassword);
        editPhone = (MaterialEditText)findViewById(R.id.editPhone);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        //Initialize firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                final ProgressDialog mDialog  = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wating......");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() 
                {
                    
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) 
                    {   
                        //check if user exists in database
                        if(dataSnapshot.child(editPhone.getText().toString()).exists())
                        {
                            //Get USer Information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class); 
                            if(user.getPassword().equals(editPassword.getText().toString()))
                            {
                                Toast.makeText(SignIn.this, "Sign In Succsesful !",Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(SignIn.this, "Sign In not Succsesfull !", Toast.LENGTH_SHORT).show();
                            }
                        }else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User doesen't exist in database !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) 
                    {

                    }
                });
            }
        });
    }
}