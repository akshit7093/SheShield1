package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mButton = findViewById(R.id.button);
        mButton.setOnClickListener(v -> {
            try {
                ProcessBuilder pb = new ProcessBuilder("/usr/bin/python", "/Users/joyaljijo/Desktop/CODE/python/chilaaa.py");
                Process p = pb.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    // process output from the Python script here
                }
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
