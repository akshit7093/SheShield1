package com.example.call;// Import the required classes
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the app has the permission to make phone calls
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission if not granted
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            // Permission already granted, initiate the call
            makePhoneCall();
        }
    }

    private void makePhoneCall() {
        // Check if the phone number is not empty
        // Replace with the phone number you want to call
        String phoneNumber = "9910319973";
        phoneNumber.trim().length();
        // Create the call intent
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));

        // Check if the device has the capability to make phone calls
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, display a message
            Toast.makeText(this, "Permission not granted to make phone calls", Toast.LENGTH_SHORT).show();
        } else {
            // Permission granted, start the call
            startActivity(callIntent);
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