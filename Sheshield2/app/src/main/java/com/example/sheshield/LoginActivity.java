package com.example.sheshield;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView btn = findViewById(R.id.textviewsignup);
        btn.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));
        Button register = findViewById(R.id.btnregister);
        register.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,Home.class)));

    }
}

