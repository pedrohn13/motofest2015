package br.edu.ufcg.embedded.motofest.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.activity.SplashScreenActivity;
import br.edu.ufcg.embedded.motofest.model.User;
import br.edu.ufcg.embedded.motofest.utils.TaskMessage;


public class RegistrationIntentService extends IntentService {
    private static final String LOG = "LOG";
    private SharedPreferences preferences, sharedPreferences;

    public RegistrationIntentService() {
        super(LOG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPreferences = getSharedPreferences(SplashScreenActivity.PREFERENCE_NAME, MODE_PRIVATE);
        boolean conectado = sharedPreferences.getBoolean(SplashScreenActivity.USER_STATUS, false);
        String idUser = sharedPreferences.getString(SplashScreenActivity.USER_ID, "0");

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean status = preferences.getBoolean("status", false);
        synchronized (LOG) {
            InstanceID instanceID = InstanceID.getInstance(this);
            try {
                if (!status && conectado && !idUser.equals("0")) {
                    String token = instanceID.getToken(MainActivity.SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    Log.i(LOG, "TOKEN: " + token);

                    sendRegistrationId(token);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRegistrationId(String token) {
        //DataSource dataSource = new DataSource(this);
        //User user = dataSource.getUser();

        String idPlus = sharedPreferences.getString(SplashScreenActivity.USER_ID, "0");
        String name = sharedPreferences.getString(SplashScreenActivity.USER_NOME, "");
        String email = sharedPreferences.getString(SplashScreenActivity.USER_EMAIL, "");
        String urlPhoto = sharedPreferences.getString(SplashScreenActivity.USER_URL_PHOTO, "");

        User user = new User(0, idPlus, token, name, email, urlPhoto);

        if (user != null) {
            user.setRegistrationId(token);
            TaskMessage taskMessage = new TaskMessage(getApplicationContext(), null, user, null, null);
            taskMessage.execute();
            preferences.edit().putBoolean("status", token != null && token.trim().length() > 0).apply();
        } else {
            preferences.edit().putBoolean("status", false).apply();
        }

//        NetworkConnection
//                .getInstance(this)
//                .execute(new WrapObjToNetwork(user, "save-user"), RegistrationIntentService.class.getName());
    }
}
