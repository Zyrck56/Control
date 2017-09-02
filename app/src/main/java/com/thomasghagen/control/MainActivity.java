package com.thomasghagen.control;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    public EditText ipaddress;
    public EditText port;
    public EditText message;
    Utils socket = new Utils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences socketinfo = getSharedPreferences("F_socketinfo", 0);
        ipaddress = (EditText)findViewById(R.id.ipaddress);
        ipaddress.setText(socketinfo.getString("ipaddress", "192.168.0.0"));

        port = (EditText)findViewById(R.id.port);
        port.setText(socketinfo.getString("port", "0"));

        Button button = (Button)findViewById(R.id.gobutton);
        button.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        String S_incomingMessage;
        @Override
        public void onClick(View view) {
            ipaddress = (EditText)findViewById(R.id.ipaddress);
            port = (EditText)findViewById(R.id.port);
            message = (EditText)findViewById(R.id.message);

            final String S_ipaddress = ipaddress.getText().toString();
            final int I_port;
            String S_port = port.getText().toString();
            if (S_port.isEmpty()) {
                I_port = 0;
            } else {
                I_port = Integer.parseInt(S_port);
            }

            final String S_message = message.getText().toString();
            /*
            Uri uriURL = Uri.parse("http://www.thomasghagen.com");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriURL);
            if (launchBrowser.resolveActivity(getPackageManager()) != null) {
                startActivity(launchBrowser);
            }
            */
            if (S_ipaddress.isEmpty()) {
                //Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                ipaddress.setError("Field cannot be empty.");

            } else if (S_port.isEmpty()) {
                port.setError("Field cannot be empty.");
            }
            else {

                SharedPreferences socketinfo = getSharedPreferences("F_socketinfo", 0);
                SharedPreferences.Editor editor = socketinfo.edit();
                editor.putString("ipaddress", S_ipaddress);
                editor.putString("port", S_port);
                editor.commit();

                socket.sendData(S_ipaddress, I_port, S_message);

                TextView textView = (TextView) findViewById(R.id.incomingmessage);
                textView.setText("Response:\n" + S_incomingMessage);

                Intent intent = new Intent(getApplicationContext(), ControlActivity.class);
                startActivity(intent);
            }
        }
    };


}
