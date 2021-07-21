package com.grupo5.dangerzone.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.grupo5.dangerzone.Aplicacion;
import com.grupo5.dangerzone.model.GeoPunto;
import com.grupo5.dangerzone.model.Lugar;
import com.grupo5.dangerzone.model.TipoLugar;

public class LugaresBD extends SQLiteOpenHelper implements RepositorioLugares {

    Context contexto;

    public LugaresBD(Context contexto) {
        super(contexto,"lugares",null,1);
        this.contexto = contexto;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lugares (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "direccion TEXT, " +
                "longitud REAL, " +
                "latitud REAL, " +
                "tipo INTEGER, " +
                "foto TEXT, " +
                "telefono INTEGER, " +
                "url TEXT, " +
                "comentario TEXT, " +
                "fecha BIGINT, " +
                "valoracion REAL)");
        db.execSQL("INSERT INTO lugares VALUES (null, " + "'UIS', " +
                "'Calle 9 # 27, Bucaramanga, Santander', " + "-73.121, 7.1377, " +
                TipoLugar.EDUCACION.ordinal() + ", '', 6344000, " +
                "'https://www.uis.edu.co/', " + "'Una de las mejores universidades de Colombia.', " +
                System.currentTimeMillis() + ", 5.0)");
        db.execSQL("INSERT INTO lugares VALUES (null, " + "'Estadio Atanasio Girardot', " +
                "'Cra. 74 # 48010', -75.59013,6.256864, " +
                TipoLugar.DEPORTE.ordinal() + ", '', 0, " +
                "'http://comunasdemedellin.com/', "+ "'Estadio de la ciudad de medellín', " +
                System.currentTimeMillis() +", 4.5)");
        db.execSQL("INSERT INTO lugares VALUES (null, " + "'Movistar Arena', " +
                "'Diagonal. 61c #26-36, Bogotá, Cundinamarca'," +  "-74.07695,4.64888," +
                TipoLugar.ESPECTACULO.ordinal() + ", '', 5470183, " +
                "'https://movistararena.co/', " + "'Centro de eventos en Bogotá', " +
                System.currentTimeMillis() + ", 4.0)");
        db.execSQL("INSERT INTO lugares VALUES (null, " + "'Páramo de Santurbán', " +
                "'Silos, Santander'," + "-72.90982,7.2466845, " +
                TipoLugar.NATURALEZA.ordinal() + ", '', 0, " + "'', " +
                "'Centro de eventos en Bogotá', " +
                System.currentTimeMillis() + ", 4.0)");
        db.execSQL("INSERT INTO lugares VALUES (null, " + "'Loma Mesa de Ruitoque', " +
                "'Loma Mesa de Ruitoque, Floridablanca, Santander',0,0, " +
                TipoLugar.BAR.ordinal() + ", '', 0, " + "'', " +
                "'Ubicación natural', " +
                System.currentTimeMillis() + ", 4.0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    @Override
    public Lugar elemento(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM lugares WHERE _id = " + id, null);
        try {
            if (cursor.moveToNext()) return extraeLugar(cursor);
            else throw new SQLException("Error al acceder al elemento _id = " + id);
        } catch (Exception e) {
            Log.d("TAG","error elemnto lugares db exec " + e.getMessage());
            throw e;
        } finally {
            if (cursor!=null) cursor.close();
        }
    }

    @Override
    public void añade(Lugar lugar) { }

    @Override
    public int nuevo() { return 0; }

    @Override
    public void borrar(int id) { }

    @Override
    public int tamaño() { return 0; }

    @Override
    public void actualiza(int id, Lugar lugar) { }

    public static Lugar extraeLugar(Cursor cursor) {
        Lugar lugar = new Lugar();
        lugar.setNombre(cursor.getString(1));
        lugar.setDireccion(cursor.getString(2));
        lugar.setPosicion(new GeoPunto(cursor.getDouble(3), cursor.getDouble(4)));
        lugar.setTipo(TipoLugar.values()[cursor.getInt(5)]);
        lugar.setFoto(cursor.getString(6));
        lugar.setTelefono(cursor.getInt(7));
        lugar.setUrl(cursor.getString(8));
        lugar.setComentario(cursor.getString(9));
        lugar.setFecha(cursor.getLong(10));
        lugar.setValoracion(cursor.getFloat(11));
        return lugar;
    }

    public Cursor extraeCursor() {
        //String consulta = "SELECT * FROM lugares";
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(contexto);
        String consulta;
        switch (pref.getString("orden", "0")) {
            case "0":
                consulta = "SELECT * FROM lugares ";
                break;
            case "1":
                consulta = "SELECT * FROM lugares ORDER BY valoracion DESC";
                break;
            default:
                double lon = ((Aplicacion) contexto.getApplicationContext()).posicionActual.getLongitud();
                double lat = ((Aplicacion) contexto.getApplicationContext()).posicionActual.getLatitud();
                consulta = "SELECT * FROM lugares ORDER BY " + "(" + lon + "-longitud)*(" + lon + "-longitud) + " +
                        "(" + lat + "-latitud )*(" + lat + "-latitud )";
                break;
        }
        consulta += " LIMIT " + pref.getString("maximo","?");
        SQLiteDatabase bd = getReadableDatabase();
        return bd.rawQuery(consulta, null);
    }
}
