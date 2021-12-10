package com.example.automaticwateringsystem;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class SendMessage extends AsyncTask<String, Void, Void> {

    private Exception exception;
    @Override
    protected Void doInBackground(String... params) {

        try {

            try {

                Socket socket = new Socket("150.128.48.167", 8000);
                PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                outToServer.println(params[0]);
                outToServer.flush();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }  catch (Exception e) {

            this.exception = e;
            return null;
        }
        return null;
    }
}
