package com.grupo5.dangerzone.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.grupo5.dangerzone.Aplicacion;
import com.grupo5.dangerzone.Firebase.AdaptadorLugaresFirestore;
import com.grupo5.dangerzone.Firebase.LugaresAsinc;
import com.grupo5.dangerzone.R;
import com.grupo5.dangerzone.data.LugaresBD;
import com.grupo5.dangerzone.model.Lugar;
import com.grupo5.dangerzone.use_cases.CasosUsoActividades;
import com.grupo5.dangerzone.use_cases.CasosUsoLocalizacion;
import com.grupo5.dangerzone.use_cases.CasosUsoLugar;

public class MainActivity extends AppCompatActivity {

    // private RepositorioLugares lugares;
    // public AdaptadorLugares adaptador;
    private AdaptadorLugaresBD adaptador;
    private LugaresBD lugares;
    private CasosUsoLugar usoLugar;
    private CasosUsoActividades usoActividades;
    private CasosUsoLocalizacion usoLocalizacion;
    private RecyclerView recyclerView;
    private static MainActivity currentcontext;
    static final int RESULTADO_PREFERENCIAS = 0;
    private static final int SOLICITUD_PERMISO_LOCALIZACION = 1;

    // ADAPTOR DE FIRESTORE
    private AdaptadorLugaresFirestore adaptadorLugaresFirestore;
    private LugaresAsinc lugaresAsinc;
    private CollectionReference instanciaMain = FirebaseFirestore.getInstance()
            .collection("Lugares");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        adaptador = ((Aplicacion) getApplication()).adaptador;
        // recyclerView.setAdapter(adaptador);
        lugares = ((Aplicacion) getApplication()).lugares;
        lugaresAsinc = ((Aplicacion) getApplication()).lugaresAsinc;
        // usoLugar = new CasosUsoLugar(this, lugares, adaptador);
        usoLugar = new CasosUsoLugar(this, lugaresAsinc, adaptadorLugaresFirestore);
        usoActividades = new CasosUsoActividades(this);
        usoLocalizacion = new CasosUsoLocalizacion(this, SOLICITUD_PERMISO_LOCALIZACION);

        // Barra de acciones
        Toolbar toolbar = findViewById(R.id.toolbar_Main);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout_Main);
        toolbar.setTitle(getTitle());

        // Firestore
        cargarInfoFromFirestore();
        adaptadorLugaresFirestore.startListening();

        // Boton flotante FAB circular
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                // Snackbar.make(view, R.string.mensaje_fab,
                // Snackbar.LENGTH_LONG).setAction("Accion",null).show();
                // usoLugar.nuevo();
                Intent nuevo_lugar = new Intent(getApplicationContext(), EdicionLugarActivity.class);
                nuevo_lugar.putExtra("_id", "UID");
                startActivity(nuevo_lugar);
            } });

        Log.d("Tag en Main","Mensaje prueba por el logcat");

        /*
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // int position = recyclerView.getChildAdapterPosition(v);
                int position = (Integer) v.getTag();
                Log.d("Posicion onCreate","Posicion adaptador: " + position);
                usoLugar.mostrar(position);
            }
        });*/

        // Importar lugaresDB a cloud firestore, solo se hace una vez
        // Luego de importar se debe comentar el código
        FirebaseFirestore firestoreDB_lugares = FirebaseFirestore.getInstance();
        /*for(int id=0; id<adaptador.getItemCount(); id++) {
            Log.d("MAIN","tamaño base datos ->" + adaptador.lugarPosicion(id));
            firestoreDB_lugares.collection("Lugares").add(adaptador.lugarPosicion(id)
            );
        }*/
    }

    // Consulta a Firestore
    public void cargarInfoFromFirestore(){
        Query query = FirebaseFirestore.getInstance()
                .collection("Lugares")
                .limit(50);
        FirestoreRecyclerOptions<Lugar> opciones = new FirestoreRecyclerOptions
                .Builder<Lugar>().setQuery(query, Lugar.class).build();
        adaptadorLugaresFirestore = new AdaptadorLugaresFirestore(opciones,this);
        Log.d("TAG MAIN","cargarfirestore " + query.toString() +
                "\nrecycler" + opciones.toString());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorLugaresFirestore);
        adaptadorLugaresFirestore.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                int posicion = recyclerView.getChildAdapterPosition(view);
                Lugar item_lugar = adaptadorLugaresFirestore.getItem(posicion);
                String id_lugar = adaptadorLugaresFirestore.getSnapshots().getSnapshot(posicion).getId();
                Log.d("MAIN","clic adaptador id" + id_lugar + "posicion " + posicion +
                        "itemlugar " + item_lugar.getTipo().getRecurso());
                Log.d("TAG","lugar elegido "+ id_lugar + " coleccion\n" +
                        FirebaseFirestore.getInstance().collection("Lugares").document(id_lugar));
                Context context = getAppContext();
                Intent intent = new Intent(context,VistaLugarActivity.class);
                intent.putExtra("lugar_fire",id_lugar);
                context.startActivity(intent);
                intent.putExtra("pos", posicion);
                intent.putExtra("icono_recurso", item_lugar.getTipo().getRecurso());
                context.startActivity(intent);
            }
        });
    }

    // Mostrar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ajustes) {
            usoActividades.lanzarPreferencias(RESULTADO_PREFERENCIAS);
            return true;
        }
        if (id == R.id.acercaDe) {
            usoActividades.lanzarAcercaDe();
            return true;
        }
        /*if (id == R.id.menu_buscar) {
            lanzarVistaLugar(null);
            return true;
        }*/
        if (id == R.id.menu_usuario) {
            usoActividades.lanzarUsuario();
            return true;
        }
        if (id == R.id.menu_mapa) {
            usoActividades.lanzarMapa();
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
        entrada.setText("1");
        entrada.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this)
                .setTitle("Selección de lugar")
                .setMessage("indica su id:")
                .setIcon(R.mipmap.icon_app_round)
                .setView(entrada)
                .setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String id = entrada.getText().toString();
                        Log.d("TAG"," buscar ");
                        usoLugar.mostrar(id);
                        // if(id > lugares.tamaño()) {
                        /*if(id > adaptador.getItemCount()) {
                            Log.d("Id lugares", "Ha ingresado un Id superior, se mostrará el último objeto");
                            usoLugar.mostrar(adaptador.getItemCount() - 1);
                        } else usoLugar.mostrar(id);*/
                    }
                })
                .setNegativeButton(R.string.negative_button, null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SOLICITUD_PERMISO_LOCALIZACION &&
                grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            usoLocalizacion.permisoConcedido();
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == RESULTADO_PREFERENCIAS) {
            adaptador.setCursor(lugares.extraeCursor());
            adaptador.notifyDataSetChanged();
        }*/
    }

    public static MainActivity getCurrentContext() {
        return currentcontext;
    }

    public static Context getAppContext() {
        return MainActivity.getCurrentContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag MA", "onresume main ");
        usoLocalizacion.activar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag MA", "onpause main ");
        usoLocalizacion.desactivar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptadorLugaresFirestore.startListening();
        currentcontext = this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorLugaresFirestore.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adaptadorLugaresFirestore.stopListening();
    }
}