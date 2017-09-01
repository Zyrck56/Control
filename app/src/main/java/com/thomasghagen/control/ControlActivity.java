package com.thomasghagen.control;

import android.content.SharedPreferences;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControlActivity extends AppCompatActivity {
    String S_ipaddress;
    String S_port;
    int I_port;


    //SimpleDateFormat ft = new SimpleDateFormat("MM.dd hh:mm:ss:SS");
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

        Button upbutton = (Button) findViewById(R.id.upbutton);
        Button leftbutton = (Button) findViewById(R.id.leftbutton);
        Button rightbutton = (Button) findViewById(R.id.rightbutton);
        Button downbutton = (Button) findViewById(R.id.downbutton);

        upbutton.setOnClickListener(uplistener);
        leftbutton.setOnClickListener(leftlistener);
        rightbutton.setOnClickListener(rightlistener);
        downbutton.setOnClickListener(downlistener);


    }

    private View.OnClickListener uplistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendData("up");
        }
    };

    private View.OnClickListener leftlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendData("left");
        }
    };

    private View.OnClickListener rightlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendData("right");
        }
    };

    private View.OnClickListener downlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendData("down");
        }
    };

    private void sendData(final String outgoingMessage) {
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

}
