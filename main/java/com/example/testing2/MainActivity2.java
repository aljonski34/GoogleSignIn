package com.example.testing2;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity2 extends AppCompatActivity {

    TextView  txtname, txtemail;

    FirebaseDatabase firebaseDatabase;

    ImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        txtname = findViewById(R.id.names);
        txtemail = findViewById(R.id.emails);
        photo = findViewById(R.id.imageView);

        String userid = getIntent().getStringExtra("Uid");
        NavigationDrawerInfo(userid);



    }

    private void NavigationDrawerInfo(String userid){


        firebaseDatabase.getReference().child("users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    Users user = snapshot.getValue(Users.class);
                    String name = user.getName();
                    String email = user.getEmail();
                    String photos = user.getImage();
                    txtname.setText(name);
                    txtemail.setText(email);


                    Glide.with(MainActivity2.this)
                            .load(photos)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .circleCrop()
                            .into(photo);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}