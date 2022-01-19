package com.example.automaticwateringsystem;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;


public class SendMessage extends AsyncTask<String, Void, String> {

    private Exception exception;
    private String url;
    private Handler handler;

    public SendMessage(String url){
        this.url = url;
        this.handler = null;
    }

    public SendMessage(Handler handler){
        this.handler = handler;
    }



    @Override
    protected String doInBackground(String... params) {


        if(handler != null){
                SystemClock.sleep(300000);
                showDisplayMessage("DATOS");
                return null;
        }else {

            try {

                try {
                    URL servidor = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) servidor.openConnection();
                    connection.connect();
                    BufferedReader entrada = new BufferedReader(new
                            InputStreamReader(connection.getInputStream()));
                    return entrada.readLine();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            } catch (Exception e) {

                this.exception = e;
                return null;
            }
            return null;
        }
    }

    public void showDisplayMessage(String displayMessage) {
        Message msg = new Message();
        msg.arg1 = 0;
        msg.obj = displayMessage.replaceAll("_", " ");
        handler.sendMessage(msg);
    }



}
