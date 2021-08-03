package com.grupo5.dangerzone.use_cases;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.grupo5.dangerzone.Firebase.AdaptadorLugaresFirestore;
import com.grupo5.dangerzone.Firebase.LugaresAsinc;
import com.grupo5.dangerzone.data.LugaresBD;
import com.grupo5.dangerzone.model.GeoPunto;
import com.grupo5.dangerzone.model.Lugar;
import com.grupo5.dangerzone.presentation.AdaptadorLugaresBD;
import com.grupo5.dangerzone.presentation.EdicionLugarActivity;
import com.grupo5.dangerzone.presentation.VistaLugarActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CasosUsoLugar {

    private Activity actividad;
    private LugaresBD lugares;
    private AdaptadorLugaresBD adaptador;
    // private RepositorioLugares lugares;

    // FIREBASE
    private AdaptadorLugaresFirestore adaptadorLugaresFirestore;
    public LugaresAsinc lugaresAsinc;
    private CollectionReference instanciaColeccion = FirebaseFirestore.getInstance()
            .collection("Lugares");

    // Constructor de la clase
    public CasosUsoLugar(Activity actividad, LugaresAsinc lugares, AdaptadorLugaresFirestore adaptador) {
        this.actividad = actividad;
        this.lugaresAsinc = lugares;
        this.adaptadorLugaresFirestore = adaptador;
        Query query = FirebaseFirestore.getInstance().collection("Lugares").limit(50);
        FirestoreRecyclerOptions<Lugar> opciones = new FirestoreRecyclerOptions
                        .Builder<Lugar>().setQuery(query, Lugar.class).build();
        adaptadorLugaresFirestore = new AdaptadorLugaresFirestore(opciones, actividad.getApplicationContext());
    }

    // Operaciones básicas
    public void mostrar(String pos) {
        Intent mostrar = new Intent(actividad, VistaLugarActivity.class);
        mostrar.putExtra("pos", pos);
        actividad.startActivity(mostrar);
    }

    // Editar
    public void editar(int pos, int codigoSolicitud) {
        Intent intent_ed_lugar = new Intent(actividad, EdicionLugarActivity.class);
        intent_ed_lugar.putExtra("pos", pos);
        actividad.startActivityForResult(intent_ed_lugar,codigoSolicitud);
    }

    public void actualizaPosLugar(int pos, Lugar lugar) {
        String id = adaptadorLugaresFirestore.getKey(pos);
        guardar(id, lugar);
    }

    // Guardar
    public void guardar(String id, Lugar nuevoLugar) {
        //lugares.actualiza(id, nuevoLugar);
        lugaresAsinc.actualiza(id, nuevoLugar);
        adaptadorLugaresFirestore.notifyDataSetChanged();
        //adaptador.setCursor(lugares.extraeCursor());
        //adaptador.notifyDataSetChanged();
    }

    // Borrar
    public void borrar(final String id) {
        // Log.d("usoslugar"," tamaño " + adaptadorLugaresFirestore.getPos(id));
        new AlertDialog.Builder(actividad)
                .setTitle("Borrado de lugar")
                .setMessage("¿Seguro de eliminar este lugar?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int  whichButton) {
                        instanciaColeccion.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(actividad.getApplicationContext(), "Lugar eliminado",
                                        Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(actividad.getApplicationContext(), "Error al eliminar lugar firestore "
                                        + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        // adaptador.setCursor(lugares.extraeCursor());
                        adaptadorLugaresFirestore.notifyDataSetChanged();
                        actividad.finish();
                    }})
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // Nuevo
    public void nuevo() {
        String id = lugaresAsinc.nuevo();
        /*int id = lugares.nuevo();
        GeoPunto posicion = ((Aplicacion) actividad.getApplication()).posicionActual;
        if (!posicion.equals(GeoPunto.SIN_POSICION)) {
            Lugar lugar = lugares.elemento(id);
            lugar.setPosicion(posicion);
            lugares.actualiza(id, lugar);
        }*/
        Intent nuevo_lugar = new Intent(actividad, EdicionLugarActivity.class);
        nuevo_lugar.putExtra("_id", id);
        actividad.startActivity(nuevo_lugar);
    }

    // INTENCIONES
    public void compartir(Lugar lugar) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,"Observa este lugar " + lugar.getNombre() + " - " +
                lugar.getUrl() + "\n" + lugar.getFoto());
        actividad.startActivity(i);
    }

    public void llamarTelefono(Lugar lugar) {
        actividad.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + lugar.getTelefono())));
    }

    public void verPgWeb(Lugar lugar) {
        actividad.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(lugar.getUrl())));
    }

    public final void verMapa(Lugar lugar) {
        double lat = lugar.getPosicion().getLatitud();
        double lon = lugar.getPosicion().getLongitud();
        Uri uri = lugar.getPosicion() != GeoPunto.SIN_POSICION ?
                Uri.parse("geo:0,0?q="+Uri.encode(lugar.getDireccion())) :
                Uri.parse("geo:" + lat + ',' + lon+"?z=18&q="+Uri.encode(lugar.getDireccion()));
        Log.d("tag casos uso lugar", "vermapa "  + uri + " " + Uri.encode(lugar.getDireccion()) +
                        "\n" + lugar.getPosicion() + " geopto " + GeoPunto.SIN_POSICION);
        actividad.startActivity(new Intent("android.intent.action.VIEW", uri));
    }

    // FOTOGRAFIAS
    public void ponerDeGaleria(int codigoSolicitud) {
        String action;
        if(android.os.Build.VERSION.SDK_INT >= 19) {
            // API 19 - Kitkat
            action = Intent.ACTION_OPEN_DOCUMENT;
        } else {
            action = Intent.ACTION_PICK;
        }
        Intent i = new Intent(action, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        actividad.startActivityForResult(i, codigoSolicitud);
    }

    public void ponerFoto(int pos, String uri, ImageView imageView) {
        // Lugar lugar = lugares.elemento(pos);
        Lugar lugar = adaptador.lugarPosicion(pos);
        lugar.setFoto(uri);
        visualizarFoto(lugar, imageView);
        actualizaPosLugar(pos, lugar);
    }

    public void visualizarFoto(Lugar lugar, ImageView imageView) {
        if (lugar.getFoto() != null && !lugar.getFoto().isEmpty()) {
            imageView.setImageBitmap(reduceBitmap(actividad, lugar.getFoto(), 1024, 1024));
            // imageView.setImageURI(Uri.parse(lugar.getFoto()));
        } else {
            imageView.setImageBitmap(null);
        }
    }

    public Uri tomarFoto(int codigoSolicitud) {
        try {
            Uri uriUltimaFoto;
            File file = File.createTempFile("img_" + (System.currentTimeMillis() / 1000),
                    ".jpg" , actividad.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            if (Build.VERSION.SDK_INT >= 24) {
                uriUltimaFoto = FileProvider.getUriForFile( actividad, "grupo5.DangerZone.fileProvider", file);
            } else {
                uriUltimaFoto = Uri.fromFile(file);
            }
            Intent intento_tomarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intento_tomarFoto.putExtra(MediaStore.EXTRA_OUTPUT, uriUltimaFoto);
            actividad.startActivityForResult(intento_tomarFoto, codigoSolicitud);
            return uriUltimaFoto;
        } catch (IOException ex) {
            Toast.makeText(actividad, "Error al crear fichero de imagen", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private Bitmap reduceBitmap(Context contexto, String uri, int maxAncho, int maxAlto) {
        try {
            InputStream input = null;
            Uri u = Uri.parse(uri);
            if (u.getScheme().equals("http") || u.getScheme().equals("https")) {
                input = new URL(uri).openStream();
            } else {
                input = contexto.getContentResolver().openInputStream(u);
            }
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = (int) Math.max(Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            Log.d("TAG cul","tamaño foto " + (int) Math.max(options.outWidth / maxAncho,
                    options.outHeight / maxAlto));
            return BitmapFactory.decodeStream(input,null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Fichero/recurso de imagen no encontrado", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Toast.makeText(contexto, "Error accediendo a imagen", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }
}
