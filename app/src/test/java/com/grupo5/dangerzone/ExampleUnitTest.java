package com.grupo5.dangerzone;

import com.grupo5.dangerzone.data.LugaresLista;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Aplicacion apps = new Aplicacion();
    LugaresLista lugares = new LugaresLista();

    @Test
    public void startingMain() {
        int places = lugares.tamaño();
        int startPlacesMain = 4;
        assertEquals( "Se inicializan luagares diferentes a 4.", startPlacesMain, places);
    }

    @Test
    public void sizeListaLugares() {
        int places = lugares.tamaño();
        int minPlacesMain = 0;
        assertTrue("No hay lugares almacenados.", places >= minPlacesMain);
    }

    @Test
    public void executeAplication() {
        assertNotNull("Clase aplicaciones es nula.", apps);
    }
}