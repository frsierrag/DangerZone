package com.grupo5.dangerzone;

import android.app.Application;

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
