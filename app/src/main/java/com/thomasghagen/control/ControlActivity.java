package com.thomasghagen.control;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ControlActivity extends AppCompatActivity {
    String S_ipaddress;
    String S_port;
    int I_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences socketinfo = getSharedPreferences("F_socketinfo", 0);
        S_ipaddress = socketinfo.getString("ipaddress", "192.168.0.0");
        S_port = socketinfo.getString("port", "0");
        I_port = Integer.parseInt(S_port);

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
            final String outgoingMessage = "Left: " + Integer.toString(i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket client = new Socket(S_ipaddress, I_port);
                        DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                        dataOutputStream.writeUTF(outgoingMessage);

                        DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                        String S_incomingMessage = dataInputStream.readUTF();
                        Log.i("incoming message", S_incomingMessage);

                        client.close();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            seekBar.setProgress(50);
        }
    };

    VerticalSeekBar.OnSeekBarChangeListener rightlistener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            final String outgoingMessage = "Right: " + Integer.toString(i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket client = new Socket(S_ipaddress, I_port);
                        DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                        dataOutputStream.writeUTF(outgoingMessage);

                        DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                        String S_incomingMessage = dataInputStream.readUTF();
                        Log.i("incoming message", S_incomingMessage);

                        client.close();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }


        public void onStopTrackingTouch(SeekBar seekBar) {

            Log.i("test", Integer.toString(seekBar.getProgress()));
        }
    };
}
