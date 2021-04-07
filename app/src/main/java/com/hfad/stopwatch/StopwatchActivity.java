package com.hfad.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import java.util.Locale;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {

    // Use seconds, running and wasRunning respectively to record the number of seconds passed, whether the stopwatch is running and whether the stopwatch was running before the activity was paused.
    // Number of seconds displayed on the stopwatch.
    private int seconds = 0;

    //Check whether the stopwatch is running
    private boolean running;

    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            //Get the previous state of the stopwatch if the activity has been destroyed and recreated
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    //Save the state of the stopwatch if it's about to be destroyed
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    //Stop the stopwatch when the activity is paused
    @Override
    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //If the activity is resumed start the stopwatch again if it was running previously.
    @Override
    protected void onResume(){
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    //Start the stopwatch running when the start button is clicked.
    //The method gets called when the Start button is clicked.
    public void onClickStart(View view){
        running = true;
    }

    //Stop the stopwatch running when the stop button is clicked.
    //The method gets called when the stop button is clicked.
    public void onClickStop(View view){
        running = false;
    }

    //Reset the stopwatch when the rest button is clicked
    //The method gets called when the reset button is clicked.
    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        //Get the text view
        final TextView timeView = (TextView)findViewById(R.id.time_view);

        //Creates a new Handler
        final Handler handler = new Handler();

        // Call the post() method passing in new Runnable.
        //The post() method processes code without a delay, so the code in the Runnable will run almost immediately.
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;


                //Format the seconds into hours, minutes, and seconds
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                //Set the text view text.
                timeView.setText(time);

                //If running is true, increment the seconds variable.
                if (running) {
                    seconds++;
                }

                //Post the code again with a delay of 1 second
                handler.postDelayed(this, 1000);
            }
        });
    }
}
