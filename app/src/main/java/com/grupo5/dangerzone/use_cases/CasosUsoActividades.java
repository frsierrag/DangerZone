package com.grupo5.dangerzone.use_cases;

import android.app.Activity;
import android.content.Intent;

import com.grupo5.dangerzone.presentation.AcercaDeActivity;
import com.grupo5.dangerzone.presentation.PreferenciasActivity;

public class CasosUsoActividades {

    protected Activity actividad;

    // Constructor de la clase
    public CasosUsoActividades(Activity actividad) {
        this.actividad = actividad;
    }

    public void lanzarAcercaDe() {
        actividad.startActivity(new Intent(actividad, AcercaDeActivity.class));
    }

    public void lanzarPreferencias(int codigoSolicitud) {
        actividad.startActivityForResult(new Intent(actividad, PreferenciasActivity.class), codigoSolicitud);
    }
}
