package com.grupo5.dangerzone.presentation;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grupo5.dangerzone.Aplicacion;
import com.grupo5.dangerzone.R;
import com.grupo5.dangerzone.model.GeoPunto;
import com.grupo5.dangerzone.model.Lugar;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mapa;
    private AdaptadorLugaresBD adaptador;
    // private RepositorioLugares lugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        adaptador = ((Aplicacion)getApplication()).adaptador;
        // lugares = ((Aplicacion) getApplication()).lugares;
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
        }
        // if (lugares.tamaño() > 0) {
        if (adaptador.getItemCount() > 0) {
            // GeoPunto p = lugares.elemento(0).getPosicion();
            GeoPunto p = adaptador.lugarPosicion(0).getPosicion();
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(p.getLatitud(), p.getLongitud()), 12));
        }
        // for (int n = 0; n<lugares.tamaño(); n++) {
        //    Lugar lugar = lugares.elemento(n);
        for (int n=0; n<adaptador.getItemCount(); n++) {
            Lugar lugar = adaptador.lugarPosicion(n);
            GeoPunto p = lugar.getPosicion();
            if (p != null && p.getLatitud() != 0) {
                Bitmap iGrande = BitmapFactory.decodeResource( getResources(), lugar.getTipo().getRecurso());
                Bitmap icono = Bitmap.createScaledBitmap(iGrande, iGrande.getWidth() / 7,
                        iGrande.getHeight() / 7, false);
                mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(p.getLatitud(), p.getLongitud()))
                        .title(lugar.getNombre()).snippet(lugar.getDireccion())
                        .icon(BitmapDescriptorFactory.fromBitmap(icono)));
            }
        }
        mapa.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        // for (int pos=0; pos<lugares.tamaño(); pos++) {
        for (int pos=0; pos<adaptador.getItemCount(); pos++) {
            if (adaptador.lugarPosicion(pos).getNombre().equals(marker.getTitle())) {
                Log.d("Nombre", "Nombre: " + adaptador.lugarPosicion(pos).getNombre());
                Log.d("Titulo", "Titulo: " + marker.getTitle());
            // if (lugares.elemento(pos).getNombre().equals(marker.getTitle())) {
                /*
                Intent intent = new Intent(this, VistaLugarActivity.class);
                intent.putExtra("pos", pos);
                startActivity(intent);
                break;
                */
            }
        }
    }
}
