package com.grupo5.dangerzone.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.grupo5.dangerzone.Aplicacion;
import com.grupo5.dangerzone.R;
import com.grupo5.dangerzone.data.RepositorioLugares;
import com.grupo5.dangerzone.use_cases.CasosUsoActividades;
import com.grupo5.dangerzone.use_cases.CasosUsoLugar;

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

    private RepositorioLugares lugares;
    private CasosUsoLugar usoLugar;
    private CasosUsoActividades usoActividades;
    private RecyclerView recyclerView;
    public AdaptadorLugares adaptador;
    static final int RESULTADO_PREFERENCIAS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adaptador = ((Aplicacion) getApplication()).adaptador;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);
        lugares = ((Aplicacion) getApplication()).lugares;
        usoLugar = new CasosUsoLugar(this,lugares);
        usoActividades = new CasosUsoActividades(this);

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
            usoActividades.lanzarPreferencias(RESULTADO_PREFERENCIAS);
            return true;
        }
        if (id == R.id.acercaDe) {
            usoActividades.lanzarAcercaDe();
            return true;
        }
        if (id == R.id.menu_buscar) {
            lanzarVistaLugar(null);
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
    public void lanzarAcercade(View view) {
        Intent abrir = new Intent(this, AcercaDeActivity.class);
        startActivity(abrir);
    }

    // Abrir vista lugar
    public void lanzarVistaLugar(View view) {
        final EditText entrada = new EditText(this);
        entrada.setText("0");
        new AlertDialog.Builder(this)
                .setTitle("Selección de lugar")
                .setMessage("indica su id:")
                .setView(entrada)
                .setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int id = Integer.parseInt(entrada.getText().toString());
                        usoLugar.mostrar(id);
                    }
                })
                .setNegativeButton(R.string.negative_button, null)
                .show();
    }

}