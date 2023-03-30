package com.example.vibrateagain;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Vibrator vibrator;
    private static final long[] VIBRATION_PATTERN = {0, 200, 100, 200};
    private static final int VIBRATION_DURATION = 1000; // 1 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button vibrateButton = findViewById(R.id.button);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        vibrateButton.setOnClickListener(v -> {
            // Vibrate with a custom vibration pattern 10 times
            for (int i = 0; i < 10; i++) {
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    VibrationEffect vibrationEffect = VibrationEffect.createWaveform(VIBRATION_PATTERN, -1);
                    vibrator.vibrate(vibrationEffect);
                }, i * VIBRATION_DURATION);
            }
        });
    }
}
