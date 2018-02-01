package com.team.a404.qommunity;

/**
 * Created by Sergio Cuadrado on 01/02/2018.
 */

import android.preference.PreferenceActivity;
import android.os.Bundle;


public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

}