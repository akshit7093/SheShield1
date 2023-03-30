package com.example.sheshield;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView btn = findViewById(R.id.textView2);
        btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
        Button register = findViewById(R.id.btnregister);
        register.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,Home.class)));



    }
}