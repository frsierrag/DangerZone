package com.grupo5.dangerzone.use_cases;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.grupo5.dangerzone.R;
import com.grupo5.dangerzone.data.RepositorioLugares;
import com.grupo5.dangerzone.presentation.VistaLugarActivity;

public class CasosUsoLugar {

    private Activity actividad;
    private RepositorioLugares lugares;

    // Constructor de la clase
    public CasosUsoLugar(Activity actividad, RepositorioLugares lugares) {
        this.actividad = actividad;
        this.lugares = lugares;
    }

    // Operaciones básicas
    public void mostrar(int pos) {
        Intent mostrar = new Intent(actividad, VistaLugarActivity.class);
        mostrar.putExtra("pos", pos); actividad.startActivity(mostrar);
    }

    // Borrar
    public void borrar(final int id) {
        new AlertDialog.Builder(actividad)
                .setTitle("Borrado de lugar")
                .setMessage("¿Seguro de eliminar este lugar?")
                .setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        lugares.borrar(id); actividad.finish();
                    }})
                .setNegativeButton(R.string.negative_button, null)
                .show();
    }
}
