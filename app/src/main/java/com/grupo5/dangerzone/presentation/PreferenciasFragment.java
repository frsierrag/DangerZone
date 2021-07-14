package com.grupo5.dangerzone.presentation;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.grupo5.dangerzone.R;

public class PreferenciasFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
