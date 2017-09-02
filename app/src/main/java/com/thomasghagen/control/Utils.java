package com.thomasghagen.control;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Thomas on 9/1/2017.
 */

public class Utils {
    String S_incomingMessage;
    public String sendData(final String ipaddress, final int port, final String outgoingMessage) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket client = new Socket(ipaddress, port);
                    DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                    dataOutputStream.writeUTF(outgoingMessage);

                    DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                    S_incomingMessage = dataInputStream.readUTF();

                    client.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return S_incomingMessage;
    }
}
