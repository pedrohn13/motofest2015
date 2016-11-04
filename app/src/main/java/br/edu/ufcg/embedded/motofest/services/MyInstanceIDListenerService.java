package br.edu.ufcg.embedded.motofest.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.iid.InstanceIDListenerService;


public class MyInstanceIDListenerService extends InstanceIDListenerService {
    public static final String LOG = "LOG";

    @Override
    public void onTokenRefresh() {
        //super.onTokenRefresh();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("status", false).apply();

        Intent intentregistration = new Intent(this, RegistrationIntentService.class);
        startService(intentregistration);

    }
}
