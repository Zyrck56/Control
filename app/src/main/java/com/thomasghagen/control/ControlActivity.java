package com.thomasghagen.control;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.SeekBar;

import com.thomasghagen.control.android.widget.VerticalSeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class ControlActivity extends AppCompatActivity {
    String S_ipaddress;
    String S_port;
    int I_port;
    Utils socket;
    VerticalSeekBar leftseekbar;
    VerticalSeekBar rightseekbar;
    String outgoingMessage;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences socketinfo = getSharedPreferences("F_socketinfo", 0);
        S_ipaddress = socketinfo.getString("ipaddress", "192.168.0.0");
        S_port = socketinfo.getString("port", "0");
        I_port = Integer.parseInt(S_port);
        outgoingMessage = "omgtest";

        leftseekbar = (VerticalSeekBar)findViewById(R.id.leftseekbar);
        rightseekbar = (VerticalSeekBar)findViewById(R.id.rightseekbar);

        leftseekbar.setProgress(50);
        rightseekbar.setProgress(50);

        leftseekbar.setOnSeekBarChangeListener(leftlistener);
        rightseekbar.setOnSeekBarChangeListener(rightlistener);

        //socket = new Utils(S_ipaddress, I_port, "");

        

    }

    SeekBar.OnSeekBarChangeListener leftlistener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            outgoingMessage = "Left: " + Integer.toString(i);

            timer = new Timer();
            timer.schedule(new Utils(S_ipaddress, I_port, outgoingMessage), 1000, 1000);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //seekBar.setProgress(50);
        }
    };

    VerticalSeekBar.OnSeekBarChangeListener rightlistener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            final String outgoingMessage = "Right: " + Integer.toString(i);
            //socket.sendData(S_ipaddress, I_port, outgoingMessage);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }


        public void onStopTrackingTouch(SeekBar seekBar) {

            Log.i("test", Integer.toString(seekBar.getProgress()));
        }
    };

}
