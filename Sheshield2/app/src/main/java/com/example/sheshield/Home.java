package com.example.sheshield;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class Home extends AppCompatActivity {

    private static final int THRESHOLD = 2000; // adjust this to set the minimum sound level to trigger the event
    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = 1024;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private boolean mShouldContinue = true;
    private AudioRecord mAudioRecord = null;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button sos1 = findViewById(R.id.btn_sos);
        Button secondbutton = findViewById(R.id.btn_profile);
        secondbutton.setOnClickListener(view -> startActivity(new Intent(Home.this, profile.class)));
        sos1.setOnClickListener(view -> startActivity(new Intent(Home.this, SOS.class)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        } else {
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE);
            startRecording();
        }
    }

    private void startRecording() {
        mAudioRecord.startRecording();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mShouldContinue) {
                    short[] audioBuffer = new short[BUFFER_SIZE];
                    mAudioRecord.read(audioBuffer, 0, BUFFER_SIZE);

                    double rms = 0;
                    for (short value : audioBuffer) {
                        rms += value * value;
                    }
                    rms = Math.sqrt(rms / audioBuffer.length);

                    if (rms > THRESHOLD) {
                        mHandler.post(() -> Toast.makeText(Home.this, "SHOUT DETECTED!", Toast.LENGTH_SHORT).show());
                        Intent SOS = new Intent(Home.this, SOS.class);
                        startActivity(SOS);
                    }

                    mHandler.postDelayed(this, 1000);
                }
            }
        };

        mHandler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mShouldContinue = false;

        if (mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE);
            startRecording();
        }
    }
}
