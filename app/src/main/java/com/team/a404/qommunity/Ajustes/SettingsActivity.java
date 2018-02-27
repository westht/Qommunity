package com.team.a404.qommunity.Ajustes;

/**
 * Created by Sergio Cuadrado on 01/02/2018.
 */

import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;

import com.team.a404.qommunity.R;


public class SettingsActivity extends PreferenceActivity {
    private Preference completa;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        completa = (Preference) findPreference("cambia");
        completa.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(SettingsActivity.this,ModificaUsuario.class);
                startActivity(intent);
                return true;
            }
        });
    }

}