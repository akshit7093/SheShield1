package com.example.vibrate;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Vibrator vibrator;
    private static final long[] VIBRATION_PATTERN = {0, 200, 100, 200};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button vibrateButton = findViewById(R.id.vibrate_button);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        vibrateButton.setOnClickListener(v -> {
            // Vibrate with a custom vibration pattern
            VibrationEffect vibrationEffect = VibrationEffect.createWaveform(VIBRATION_PATTERN, -1);
            vibrator.vibrate(vibrationEffect);
        });
    }
}