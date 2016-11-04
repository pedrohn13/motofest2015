package br.edu.ufcg.embedded.motofest.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.List;

import br.edu.ufcg.embedded.motofest.R;

public class SplashScreenActivity extends Activity implements View.OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {
    private static final int SIGN_IN_CODE = 56465;
    private static final String SILENT_MODE = "SILENT_MODE";
    private GoogleApiClient googleApiClient;
    private ConnectionResult connectionResult;

    private boolean isConsentScreenOpened;
    private boolean isSignInButtonClicked;

    public static final String PREFERENCE_NAME = "USER_PREFERENCE";

    public static final  String USER_ID = "USER_ID";
    public static final String USER_NOME = "USER_NOME";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_URL_PHOTO = "USER_URL_PHOTO";
    public static final String USER_STATUS = "USER_STATUS";

    private static final int FROM_X_DELTA = 2000;
    private static final int DURATION_MILLIS = 3300;
    private static final int VOLUME_SOUND = 3;

    // VIEWS
    private ProgressBar pbContainer;

    private SignInButton btSignInDefault;

    private RelativeLayout layoutinfoMotofest;
    private LinearLayout layoutGooglePlus;
    private Button continueBt;
    private boolean conectado;
    private ImageView logo;
    private boolean jatentouabrirmain = false;
    private boolean jatentoulogar = false;
    private CheckBox checkbox;
    private MediaPlayer musica;
    private AudioManager audioManager;
    private Animation deslocamento;
    private boolean silent;
    private boolean closed = false;
    private List<ActivityManager.RecentTaskInfo> apps;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        setTitle(R.string.app_name);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        conectado = sharedPreferences.getBoolean(USER_STATUS, false);
        silent = sharedPreferences.getBoolean(SILENT_MODE, false);

        getSettingsPreferences();

        accessViews();
/*
        if(conectado){
            ActivityManager activity_manager = (ActivityManager) getApplicationContext().getSystemService(Activity.ACTIVITY_SERVICE);
            apps = activity_manager.getRecentTasks(5, ActivityManager.RECENT_IGNORE_UNAVAILABLE );
            for (ActivityManager.RecentTaskInfo a: apps){
                if(a.id == getTaskId()){
                    chamarTelaPrincipal();
                }
            }
        }
*/
        googleApiClient = new GoogleApiClient.Builder(SplashScreenActivity.this)
                .addConnectionCallbacks(SplashScreenActivity.this)
                .addOnConnectionFailedListener(SplashScreenActivity.this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();


        deslocamento = new TranslateAnimation(FROM_X_DELTA, 0, 0, 0);
        deslocamento.setDuration(DURATION_MILLIS);

        musica = MediaPlayer.create(this, R.raw.som_splash);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        checkbox.setChecked(silent);
        layoutinfoMotofest.startAnimation(deslocamento);

        if (checkbox.isChecked()) {
            muteSound(true);
        } else {
            muteSound(false);
        }




        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(DURATION_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (!jatentouabrirmain) {
                        jatentouabrirmain = true;
                        if (conectado) {
                            chamarTelaPrincipal();
                        }
                    }

                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }
            }
        };
        timer.start();
    }

    public void getSettingsPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SettingsActivity.PREFERENCE_NAME, MODE_PRIVATE);
        boolean launcherStatus = sharedPreferences.getBoolean(SettingsActivity.SPLASH_LAUNCHER, true);
        boolean launcherSoundStatus = sharedPreferences.getBoolean(SettingsActivity.SPLASH_SOUND, true);
        silent = !launcherSoundStatus;

        if (!launcherStatus && conectado) {
            chamarTelaPrincipal();
        }
    }

    private void muteSound(boolean mute) {
        if (mute) {
            musica.setVolume(0, 0);
        } else {
            musica.setVolume(VOLUME_SOUND, VOLUME_SOUND);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
            musica.start();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closed = true;
        musica.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkbox.isChecked()) {
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    muteSound(false);
                }
            });
        } else {
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    muteSound(true);
                }
            });
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SILENT_MODE, checkbox.isChecked());
        editor.apply();

        SharedPreferences sharedPreferencesSettings = getSharedPreferences(SettingsActivity.PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editorSettings = sharedPreferencesSettings.edit();
        editorSettings.putBoolean(SettingsActivity.SPLASH_SOUND, !checkbox.isChecked());
        editorSettings.apply();

    }

    @Override
    public void onStop() {
        super.onStop();

        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SIGN_IN_CODE) {
            isConsentScreenOpened = false;

            if (resultCode != RESULT_OK) {
                isSignInButtonClicked = false;
            }

            if (!googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        }
    }

    private void chamarTelaPrincipal() {
        if (!isActivityRunning(MainActivity.class) && !closed) {
            startActivity(new Intent(this, MainActivity.class));
        }
        closed = true;
        finish();
    }

    protected Boolean isActivityRunning(Class activityClass) {
        ActivityManager activityManager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName())) {
                return true;
            }
        }

        return false;
    }

    // UTIL
    private void accessViews() {
        continueBt = (Button) findViewById(R.id.continue_app);
        layoutinfoMotofest = (RelativeLayout) findViewById(R.id.layoutinfoMotofest);

        logo = (ImageView) findViewById(R.id.splash_logo);


        layoutGooglePlus = (LinearLayout) findViewById(R.id.layoutGooglePlus);
        pbContainer = (ProgressBar) findViewById(R.id.pbContainer);

        btSignInDefault = (SignInButton) findViewById(R.id.btSignInDefault);
        btSignInDefault.setOnClickListener(SplashScreenActivity.this);

        continueBt.setVisibility(Button.GONE);
        btSignInDefault.setVisibility(SignInButton.GONE);

        checkbox = (CheckBox) findViewById(R.id.checkBox_sound);
        checkbox.setButtonDrawable(R.drawable.checkbox_selector_sound);

        continueBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USER_ID, "0");
                editor.putBoolean(USER_STATUS, true);

                editor.apply();

                chamarTelaPrincipal();
            }
        });
    }

    private void showUi(boolean status, boolean statusProgressBar) {
        if (!statusProgressBar) {
            layoutinfoMotofest.setVisibility(View.VISIBLE);
            layoutGooglePlus.setVisibility(View.VISIBLE);
            pbContainer.setVisibility(View.GONE);
        } else {
            layoutGooglePlus.setVisibility(View.GONE);
            layoutinfoMotofest.setVisibility(View.GONE);
            pbContainer.setVisibility(View.VISIBLE);
        }
    }

    private void resolveSignIn() {
        if (connectionResult != null && connectionResult.hasResolution()) {
            try {
                isConsentScreenOpened = true;
                connectionResult.startResolutionForResult(SplashScreenActivity.this, SIGN_IN_CODE);
            } catch (IntentSender.SendIntentException e) {
                isConsentScreenOpened = false;
                googleApiClient.connect();
            }
        }
    }

    private void getDataProfile() {
        Person pessoa = Plus.PeopleApi.getCurrentPerson(googleApiClient);

        if (pessoa != null) {
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String pessoaId = pessoa.getId();
            String name = pessoa.getDisplayName();
            String email = Plus.AccountApi.getAccountName(googleApiClient);
            String urlImage = pessoa.getImage().getUrl();

            editor.putString(USER_ID, pessoaId);
            editor.putString(USER_NOME, name);
            editor.putString(USER_EMAIL, email);
            editor.putString(USER_URL_PHOTO, urlImage);
            editor.putBoolean(USER_STATUS, true);

            editor.apply();

            chamarTelaPrincipal();
        } else {
            Toast.makeText(SplashScreenActivity.this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
        }
    }


    // LISTENERS
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btSignInDefault) {
            if (!googleApiClient.isConnecting()) {
               isSignInButtonClicked = true;
                showUi(false, true);
                resolveSignIn();
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        isSignInButtonClicked = false;
        showUi(true, false);
        getDataProfile();
    }


    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
        showUi(false, false);
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!conectado) {
            if (!result.hasResolution()) {
                GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), SplashScreenActivity.this, 0).show();
                return;
            }

            if (!isConsentScreenOpened) {
                connectionResult = result;

                if (jatentoulogar) {
                    btSignInDefault.setVisibility(SignInButton.VISIBLE);
                    continueBt.setVisibility(Button.VISIBLE);
                    showUi(false, false);
                }

                if (isSignInButtonClicked || !jatentoulogar) {
                    resolveSignIn();
                    jatentoulogar = true;
                }
            }
        } else {
            showUi(false, false);
        }
    }
}