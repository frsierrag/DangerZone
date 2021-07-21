package com.grupo5.dangerzone.presentation;

import android.database.Cursor;

import com.grupo5.dangerzone.data.LugaresBD;
import com.grupo5.dangerzone.data.RepositorioLugares;
import com.grupo5.dangerzone.model.Lugar;

public class AdaptadorLugaresBD extends AdaptadorLugares {

    protected Cursor cursor;

    // CONSTRUCTOR
    public AdaptadorLugaresBD(RepositorioLugares lugares, Cursor cursor) {
        super(lugares);
        this.cursor = cursor;
    }

    // MÉTODOS GET Y SET DE CURSOR
    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Lugar lugarPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return LugaresBD.extraeLugar(cursor);
    }

    public int idPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        if (cursor.getCount() > 0) {
            return cursor.getInt(0);
        } else {
            return -1;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        // super.onBindViewHolder(holder, posicion);
        Lugar lugar = lugarPosicion(posicion);
        holder.personaliza(lugar);
        holder.itemView.setTag(new Integer(posicion));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
