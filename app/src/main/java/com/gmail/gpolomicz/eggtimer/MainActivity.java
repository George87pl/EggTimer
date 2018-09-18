package com.gmail.gpolomicz.eggtimer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    final int MIN_TIME = 30;
    final int MAX_TIME = 480;

    TextView timerTextView;
    SeekBar eggTimerSeekBar;
    Button startTimerButton;
    Button stopButton;
    CountDownTimer eggCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eggTimerSeekBar = findViewById(R.id.eggTimerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        startTimerButton = findViewById(R.id.startTimerButton);
        stopButton = findViewById(R.id.stopButton);

        eggTimerSeekBar.setMax(MAX_TIME);
        eggTimerSeekBar.setProgress(MIN_TIME);

        eggTimerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < MIN_TIME) {
                    seekBar.setProgress(MIN_TIME);
                } else {
                    updateTime(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eggTimerSeekBar.setEnabled(false);
                startTimerButton.setEnabled(false);
                stopButton.setVisibility(View.VISIBLE);

                eggCountDownTimer = new CountDownTimer((eggTimerSeekBar.getProgress())*1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                        updateTime(millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {

                        timerTextView.setText("00:00");
                        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.rooster);
                        mediaPlayer.start();
                    }
                }.start();
            }
        });
    }

    public void updateTime (long time) {

        long minutes = time / 60;
        long seconds = time % 60;

        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeString);
    }

    public void reset (View view) {
        eggCountDownTimer.cancel();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
