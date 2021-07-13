package com.grupo5.dangerzone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
/*
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
        RepositorioLugares lugares = new LugaresLista();
        for (int i=0; i<lugares.tamaño(); i++){
            System.out.println(lugares.elemento(i).toString());
        }
        Log.d("Tag in msg", "Mensaje de prueba por el logcat: " + lugar.toString());
    }
*/

    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSalir = findViewById(R.id.button04);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Barra de acciones
        Toolbar toolbar = findViewById(R.id.toolbar_Main);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout_Main);
        toolbar.setTitle(getTitle());

        // Boton flotante FAB circular
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Snackbar.make(view, R.string.mensaje_fab,
                Snackbar.LENGTH_LONG).setAction("Accion",null).show();
            } });

        // lugar.toString();
        Log.d("Tag en Main","Mensaje prueba por el logcat");
    }

    // Mostrar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button, so long
        as you specify a parent activity in AndroidManifest.xm
        */

        int id = item.getItemId();
        if (id == R.id.ajustes) {
            Log.d("Tag en Main","Clic en la opcion ajustes");
            return true;
        }
        if (id == R.id.acercaDe) {
            lanzarAcercade(null);
            return true;
        }
        if (id == R.id.menu_buscar) {
            Log.d("Tag main","clic a la opcion buscar");
            return true;
        } if (id == R.id.menu_usuario) {
            return true;
        }
        if (id == R.id.menu_mapa) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Abrir acerca de
    public void lanzarAcercade(View view){
        Intent abrir = new Intent(this,AcercaDeActivity.class);
        startActivity(abrir);
    }

}