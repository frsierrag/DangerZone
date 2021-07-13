package com.grupo5.dangerzone.data;

import com.grupo5.dangerzone.model.Lugar;
import com.grupo5.dangerzone.model.TipoLugar;

import java.util.ArrayList;
import java.util.List;

public class LugaresLista implements RepositorioLugares {

    protected List<Lugar> listaLugares;

    public LugaresLista() {
        listaLugares = new ArrayList<Lugar>();
        añadeEjemplos();
    }
    public Lugar elemento(int id) {
        return listaLugares.get(id);
    }
    public void añade(Lugar lugar) {
        listaLugares.add(lugar);
    }
    public int nuevo() {
        Lugar lugar = new Lugar();
        listaLugares.add(lugar);
        return listaLugares.size()-1;
    }
    public void borrar(int id) {
        listaLugares.remove(id);
    }
    public int tamaño() {
        return listaLugares.size();
    }
    public void actualiza(int id, Lugar lugar) {
        listaLugares.set(id, lugar);
    }
    public void añadeEjemplos() {
        añade(new Lugar("UIS","Calle 9#27, Bucaramanga, Santander",
                7.1377,-73.121, TipoLugar.EDUCACION,
                6344000, "https://www.uis.edu.co/",
                "Una de las mejores universidades de Colombia",
                5));
        añade(new Lugar("Estadio Atanasio Girardot","Cra. 74 #48010, Medellín, Antioquia",
                6.256864,-75.59013, TipoLugar.DEPORTE,
                0, "http://comunasdemedellin.com/",
                "Estadio de la ciudad de medellín",
                (float) 4.5));
        añade(new Lugar("Movistar Arena","Diagonal. 61c #26-36, Bogotá, Cundinamarca",
                6.256864,-75.59013, TipoLugar.ESPECTACULO,
                5470183, "https://movistararena.co/",
                "Centro de eventos en Bogotá",
                4));
        añade(new Lugar("Páramo de Santurbán","Silos, Santander",
                7.2466845,-72.90982, TipoLugar.NATURALEZA,
                0, "SIN DATOS",
                "Lugar entre los departamentos Santander y Norte de Santander",
                4));
    }
}
