package com.grupo5.dangerzone.use_cases;

import android.app.Activity;
import android.content.Intent;

import com.grupo5.dangerzone.presentation.AcercaDeActivity;

public class CasosUsoActividades {

    protected Activity actividad;

    // Constructor de la clase
    public CasosUsoActividades(Activity actividad) {
        this.actividad = actividad;
    }

    public void lanzarAcercaDe() {
        actividad.startActivity(new Intent(actividad, AcercaDeActivity.class));
    }
}
