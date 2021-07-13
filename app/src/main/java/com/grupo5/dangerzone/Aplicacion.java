package com.grupo5.dangerzone;

import android.app.Application;

import com.grupo5.dangerzone.data.LugaresLista;
import com.grupo5.dangerzone.data.RepositorioLugares;

public class Aplicacion extends Application {

    public RepositorioLugares lugares = new LugaresLista();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // Get de repositoriolugares
    public RepositorioLugares getLugares() {
        return lugares;
    }
}
