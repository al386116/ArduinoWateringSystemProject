package com.example.automaticwateringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

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
    SendMessage sendMessage;



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
        //sendMessage = (SendMessage) new SendMessage().execute("fufifu");

    }

    public void onClickAuto(View v) {
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
    }

    public void onClickRegar(View v) {
        infoTextView.setText("- - - Mensajes de información - - -\nRegando durante 5 minutos");
        regarButton.setEnabled(false);
    }

    public void onClickAbrirToldo(View v) {
        infoTextView.setText("- - - Mensajes de información - - -\nToldo abierto");
        abrirToldoButton.setEnabled(false);
        cerrarToldoButton.setEnabled(true);
    }

    public void onClickCerrarToldo(View v) {
        infoTextView.setText("- - - Mensajes de información - - -\nToldo cerrado");
        cerrarToldoButton.setEnabled(false);
        abrirToldoButton.setEnabled(true);
    }
}