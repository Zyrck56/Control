package com.thomasghagen.control;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TimerTask;

/**
 * Created by Thomas on 9/1/2017.
 */

public class Utils extends TimerTask {
    private String S_incomingMessage;
    private String ipaddress;
    private int port;
    private String outgoingMessage;

    public Utils(String C_ipaddress, int C_port, String C_outgoingMessage) {
        ipaddress = C_ipaddress;
        port = C_port;
        outgoingMessage = C_outgoingMessage;
    }

    public String sendData() {
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

    @Override
    public void run() {
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
        //return S_incomingMessage;
    }
}
