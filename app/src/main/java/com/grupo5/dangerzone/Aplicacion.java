package com.grupo5.dangerzone;

import android.app.Application;

import com.grupo5.dangerzone.data.LugaresBD;
import com.grupo5.dangerzone.data.RepositorioLugares;
import com.grupo5.dangerzone.model.GeoPunto;
import com.grupo5.dangerzone.presentation.AdaptadorLugaresBD;

public class Aplicacion extends Application {

    // public RepositorioLugares lugares = new LugaresLista();
    // public AdaptadorLugares adaptador = new AdaptadorLugares(lugares);
    public LugaresBD lugares;
    public AdaptadorLugaresBD adaptador;
    public GeoPunto posicionActual = new GeoPunto(0.0, 0.0);

    @Override public void onCreate() {
        super.onCreate();
        lugares = new LugaresBD(this);
        adaptador = new AdaptadorLugaresBD(lugares, lugares.extraeCursor());
    }

    // @Override public void onCreate() { super.onCreate(); }

    // Get de repositoriolugares
    public RepositorioLugares getLugares() {
        return lugares;
    }
}
