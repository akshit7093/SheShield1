package com.example.unitconverter;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
    private static final int SHORT_BUFFER_SIZE = BUFFER_SIZE / 2;

    private boolean isRecording = false;

    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTextView = findViewById(R.id.status_textview);

        findViewById(R.id.start_button).setOnClickListener(view -> startRecording());
        findViewById(R.id.stop_button).setOnClickListener(view -> stopRecording());
    }

    @SuppressLint("SetTextI18n")
    private void startRecording() {
        isRecording = true;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        AudioRecord audioRecord = new AudioRecord(AUDIO_SOURCE, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE);

        audioRecord.startRecording();

        short[] audioBuffer = new short[SHORT_BUFFER_SIZE];

        while (isRecording) {
            audioRecord.read(audioBuffer, 0, audioBuffer.length);
            double rms = getRMS(audioBuffer);
            if (rms > 100) {
                statusTextView.setText("SHOUT DETECTED!");
            }
        }

        audioRecord.stop();
        audioRecord.release();
    }

    private void stopRecording() {
        isRecording = false;
    }

    private double getRMS(short[] audioBuffer) {
        int length = audioBuffer.length;
        double sum = 0;
        for (short value : audioBuffer) {
            sum += value * value;
        }
        return Math.sqrt(sum / length);
    }
}