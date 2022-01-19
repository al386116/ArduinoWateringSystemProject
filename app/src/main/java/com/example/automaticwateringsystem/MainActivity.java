package com.example.automaticwateringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView humedadID;
    TextView temperaturaID;
    TextView solID;
    TextView toldoID;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch autoID;
    Button regarButton;
    Button abrirToldoButton;
    Button cerrarToldoButton;
    TextView infoTextView;
    Handler handler;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.humedadID = (TextView) findViewById(R.id.humedadID);
        this.temperaturaID = (TextView) findViewById(R.id.temperaturaID);
        this.solID = (TextView) findViewById(R.id.solID);
        this.toldoID = (TextView) findViewById(R.id.toldoID);
        this.autoID = (Switch) findViewById(R.id.autoID);
        this.regarButton = (Button) findViewById(R.id.regarButton);
        this.abrirToldoButton = (Button) findViewById(R.id.abrirToldoButton);
        this.cerrarToldoButton = (Button) findViewById(R.id.cerrarToldoButton);
        this.infoTextView = (TextView) findViewById(R.id.infoTextView);
        autoID.setChecked(false);
        SendMessage sendMessage = new SendMessage("http://192.168.0.25:8080/");
        try {
            String mensaje = sendMessage.execute().get();
            String[] datos = mensaje.split("#");
            humedadID.setText("Humedad: " + datos[0]);
            temperaturaID.setText("Temperatura: " + datos[1]);
            solID.setText("%Sol: " + datos[2]);
            if(datos[3].equals("1")){
                toldoID.setText("Toldo: Abierto");
            }else{
                toldoID.setText("Toldo: Cerrado");
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        handler = new Handler() {
            @SuppressLint({"LongLogTag", "HandlerLeak"})
            @Override
            public void handleMessage(Message msg) {
                Log.d("handlerNetworkExecutorResult", (String) msg.obj);
                if (msg != null) {
                    if (msg.obj.equals("DATOS")) {
                        SendMessage sendMessage = new SendMessage("http://192.168.0.25:8080/");
                        try {
                            String mensaje = sendMessage.execute().get();
                            String[] datos = mensaje.split("#");
                            humedadID.setText("Humedad: " + datos[0]);
                            temperaturaID.setText("Temperatura: " + datos[1]);
                            solID.setText("%Sol: " + datos[2]);
                            if(datos[3].equals("1")){
                                toldoID.setText("Toldo: Abierto");
                            }else{
                                toldoID.setText("Toldo: Cerrado");
                            }
                            SendMessage sendMessage1 = new SendMessage(handler);
                            sendMessage1.execute();

                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        SendMessage sendMessage1 = new SendMessage(handler);
        sendMessage1.execute();
    }

    public void onClickAuto(View v) {
        SendMessage sendMessage = new SendMessage("http://192.168.0.25:8080/auto");
        try {
            String Mensaje = sendMessage.execute().get();
            if (Mensaje.equals("listo")){
                if (!autoID.isChecked()) {
                    autoID.setChecked(false);
                    regarButton.setEnabled(true);
                    abrirToldoButton.setEnabled(true);
                    cerrarToldoButton.setEnabled(true);
                } else {
                    autoID.setChecked(true);
                    regarButton.setEnabled(false);
                    abrirToldoButton.setEnabled(false);
                    cerrarToldoButton.setEnabled(false);
                }
            } else{
                if (!autoID.isChecked()) {
                    autoID.setChecked(true);
                } else {
                    autoID.setChecked(false);
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void onClickRegar(View v) throws IOException {
        SendMessage sendMessage = new SendMessage("http://192.168.0.25:8080/regar");
        try {
            String Mensaje = sendMessage.execute().get();
            if (Mensaje.equals("listo")){
                infoTextView.setText("- - - Mensajes de información - - -\nRegando durante 5 minutos");
            } else {
                infoTextView.setText("- - - Mensajes de información - - -\nError al regar");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void onClickAbrirToldo(View v) {
        SendMessage sendMessage = new SendMessage("http://192.168.0.25:8080/abrirToldo");
        try {
            String Mensaje = sendMessage.execute().get();
            if (Mensaje.equals("listo")){
                infoTextView.setText("- - - Mensajes de información - - -\nToldo abierto");
                toldoID.setText("Toldo: Abierto");
                abrirToldoButton.setEnabled(false);
                cerrarToldoButton.setEnabled(true);
            } else {
                infoTextView.setText("- - - Mensajes de información - - -\nError abriendo el toldo");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void onClickCerrarToldo(View v) {
        SendMessage sendMessage = new SendMessage("http://192.168.0.25:8080/cerrarToldo");
        try {
            String Mensaje = sendMessage.execute().get();
            if (Mensaje.equals("listo")){
                infoTextView.setText("- - - Mensajes de información - - -\nToldo cerrado");
                toldoID.setText("Toldo: Cerrado");
                abrirToldoButton.setEnabled(true);
                cerrarToldoButton.setEnabled(false);
            } else {
                infoTextView.setText("- - - Mensajes de información - - -\nError cerrando el toldo");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}