package com.example.sheshield;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SOS extends AppCompatActivity {

    private Vibrator vibrator;
    private static final long[] VIBRATION_PATTERN = {0, 200, 100, 200};
    private static final int VIBRATION_DURATION = 1000; // 1 second
    private static final int REQUEST_CALL = 1;
    private boolean cancelVibration = false; // add this line

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        // Start the vibration
        startVibration();

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startVibration() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate with a custom vibration pattern 10 times
        @SuppressLint({"MissingInflatedId", "LocalSuppress", "UseSwitchCompatOrMaterialCode"}) Switch cancelSwitch = findViewById(R.id.switch1);
        cancelSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // If the switch is toggled on, cancel the vibration and prevent the call function from running
                vibrator.cancel();
                cancelVibration = true;
            }
        });
        for (int i = 0; i < 10; i++) {
            Handler handler = new Handler();
            int finalI = i;
            handler.postDelayed(() -> {
                if (!cancelVibration) {
                    VibrationEffect vibrationEffect = VibrationEffect.createWaveform(VIBRATION_PATTERN, -1);
                    vibrator.vibrate(vibrationEffect);
                }

                // Initiate the call after the vibration loop is over
                if (finalI == 9) {
                    initiateCall();
                }
            }, i * VIBRATION_DURATION);

        }

    }

    private void initiateCall() {
        // Check if the app has the permission to make phone calls
        if (ContextCompat.checkSelfPermission(SOS.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED & cancelVibration) {
            // Request the permission if not granted
            ActivityCompat.requestPermissions(SOS.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            // Permission already granted, initiate the call
            makePhoneCall();
        }
    }

    private void makePhoneCall() {
        // Check if the phone number is not empty
        String phoneNumber = "9910319973";
        phoneNumber.trim().length();
        // Create the call intent
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));

        // Check if the device has the capability to make phone calls
        if (ContextCompat.checkSelfPermission(SOS.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, display a message
            Toast.makeText(this, "Permission not granted to make phone calls", Toast.LENGTH_SHORT).show();
        } else {
            // Permission granted, start the call
            if (!cancelVibration) {
                startActivity(callIntent);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Check if the request code is for making phone calls
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            // Check if the permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initiate the call
                makePhoneCall();
            } else {
                // Permission not granted, display a message
                Toast.makeText(this, "Permission denied to make phone calls", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
