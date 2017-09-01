package com.thomasghagen.control;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;

public class ControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        VerticalSeekBar leftseekbar = (VerticalSeekBar)findViewById(R.id.leftseekbar);
        VerticalSeekBar rightseekbar = (VerticalSeekBar)findViewById(R.id.rightseekbar);

        leftseekbar.setProgress(50);
        rightseekbar.setProgress(50);

        leftseekbar.setOnSeekBarChangeListener(leftlistener);
        rightseekbar.setOnSeekBarChangeListener(rightlistener);
        

        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    VerticalSeekBar.OnSeekBarChangeListener leftlistener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    VerticalSeekBar.OnSeekBarChangeListener rightlistener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
