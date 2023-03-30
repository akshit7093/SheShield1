package com.example.sheshield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Home extends AppCompatActivity {
    Button sos1,secondbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         sos1= findViewById(R.id.btn_sos);
         secondbutton = findViewById(R.id.btn_profile);
        secondbutton.setOnClickListener(view -> startActivity(new Intent(Home.this,profile.class)));
       sos1.setOnClickListener(view -> startActivity(new Intent(Home.this,SOS.class)));
    }
}