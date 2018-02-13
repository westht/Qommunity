package com.team.a404.qommunity;

/**
 * Created by Sergio Cuadrado on 01/02/2018.
 */

import android.content.Intent;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;


public class SettingsActivity extends PreferenceActivity {
    private ListPreference completa;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        completa = (ListPreference) findPreference("cambia");
        completa.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return true;
            }
        });
    }

}