package com.grupo5.dangerzone;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Tag in msg", "Mensaje de prueba");
    }
 */

    TextView mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lugar lugar = new Lugar("UIS","Clle 9#27",7.1377,-73.121,
                TipoLugar.EDUCACION, 6344000, "https://www.uis.edu.co/",
                "Una de las mejores universidades de Colombia", 5);
        mensaje = findViewById(R.id.txt_mensaje);
        mensaje.setText(lugar.toString());
        Log.d("Tag in msg", "Mensaje de prueba por el logcat: " + lugar.toString());
    }



}